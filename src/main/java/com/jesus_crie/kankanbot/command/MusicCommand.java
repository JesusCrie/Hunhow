package com.jesus_crie.kankanbot.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesus_crie.kankanbot.HunhowBot;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.music.GuildMusicManager;
import com.jesus_crie.kankanbot.music.YoutubeVideo;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class MusicCommand extends Command {

    public MusicCommand() {
        super("music", Collections.singletonList("m"), "Main command for the music component." +
                        "You can queue song into the player, skip tracks, pause player, ect... Use summon to connect the bot into your voice channel.",
                CommandUtils.COMMAND_PREFIX + "music <summon|play|volume|skip|pause|resume|shuffle|clear>",
                AccessLevel.EVERYONE,
                false);

        super.registerSubCommands(
                new Summon(),
                new Play(),
                new Volume(),
                new Skip(),
                new Queue(),
                new Current(),
                new Pause(),
                new Resume(),
                new Shuffle(),
                new Clear()
        );
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        if (args.length < 1)
            return false;
        if (super.getSubCommand(args[0]) != null)
            return (super.getSubCommand(args[0]).isValid(msg, args));
        return false;
    }

    @Override
    public void execute(Message msg, String[] args) {
        SubCommand command = super.getSubCommand(args[0]);

        if (command != null)
            if (command.isValid(msg, super.getSubArgs(args)))
                command.execute(msg, super.getSubArgs(args), HunhowBot.getInstance().getMusicManager().getGuildManager(msg.getGuild()));
            else
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Invalid syntax.\nUsage: `" + command.getSyntax() + "`", msg.getAuthor())).queue();
        else
            msg.getChannel().sendMessage(
                    MessageUtils.getErrorMessage("Unknow subcommand use `" + CommandUtils.COMMAND_PREFIX + "help music` to show some help", msg.getAuthor())).queue();
    }

    private static class Summon extends SubCommand {

        public Summon() {
            super("summon",
                    "Connect the bot to your current voice channel.",
                    CommandUtils.COMMAND_PREFIX + "music summon");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            VoiceChannel channel = manager.getUserChannel(msg.getAuthor());
            if (channel == null) {
                Logger.warning("User is not connected to a voice channel", LogFrom.COMMAND);
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("You are not connected to a voice channel !", msg.getAuthor())).queue();
                return;
            }
            manager.connectToChannel(channel);

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setAuthor("Connecting to " + channel.getName() + ".", null, "http://www.jesus-crie.com/img/music.png");
            builder.setColor(Color.ORANGE);

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }

    private static class Play extends SubCommand {

        public Play() {
            super("play",
                    "Queue a song or a playlist in your current channel.",
                    CommandUtils.COMMAND_PREFIX + "music play <url|id>");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return (args.length >= 1);
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            final String query = String.join(" ", args);

            msg.getChannel().sendTyping().queue();

            HunhowBot.getInstance().getMusicManager().getPlayerManager().loadItem(query, new AudioLoadResultHandler() {

                public void trackLoaded(AudioTrack track) {
                    Logger.info("Track successfully loaded !", LogFrom.MUSIC);
                    if (!manager.isConnected()) {
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("You need to summon me in your channel first !", msg.getAuthor())).queue();
                        return;
                    }

                    EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                    builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
                    builder.setColor(Color.ORANGE);
                    builder.setDescription("Queued **" + track.getInfo().title + "**");
                    msg.getChannel().sendMessage(builder.build()).queue();

                    manager.play(track);
                }

                public void playlistLoaded(final AudioPlaylist playlist) {
                    Logger.info("Playlist successfully loaded !", LogFrom.MUSIC);
                    if (!manager.isConnected()) {
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("You need to summon me in your channel first !", msg.getAuthor())).queue();
                        return;
                    }

                    new Thread(() -> {
                        for (AudioTrack track : playlist.getTracks())
                            manager.play(track);
                    }).start();

                    EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                    builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
                    builder.setColor(Color.ORANGE);
                    builder.setDescription("Queued playlist **" + playlist.getName() + "**");

                    msg.getChannel().sendMessage(builder.build()).queue();
                }

                public void noMatches() {
                    msg.getChannel().sendMessage(MessageUtils.getErrorMessage("No matches =(", msg.getAuthor())).queue();
                }

                public void loadFailed(FriendlyException e) {
                    Logger.warning("Failed to load a track !", LogFrom.MUSIC);
                    msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Can't load **" + query + "**\n" + e.getMessage(), msg.getAuthor())).queue();
                }
            });
        }
    }

    private static class Volume extends SubCommand {

        public Volume() {
            super("volume",
                    "Change the volume of the music, in percent, in the current server.",
                    CommandUtils.COMMAND_PREFIX + "music volume [percent]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
            builder.setColor(Color.ORANGE);

            if (args.length >= 1) {
                try {
                    int volume = Integer.parseInt(args[0]);
                    if (volume <= 1)
                        volume = 1;
                    else if (volume >= 100)
                        volume = 100;

                    manager.setVolume(volume);
                    builder.setDescription("Volume updated to **" + volume + "%**");
                } catch (NumberFormatException e) {
                    msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Sorry, this is not a number !", msg.getAuthor())).queue();
                }
            } else
                builder.setDescription("Volume: **" + manager.getVolume() + "%**");

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }

    private static class Skip extends SubCommand {

        public Skip() {
            super("skip",
                    "Skip the current track.",
                    CommandUtils.COMMAND_PREFIX + "music skip");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
            builder.setColor(Color.ORANGE);

            String previous = "**" + manager.getCurrentTrack().getInfo().title + "**";
            manager.skipTrack();
            builder.setDescription("Skipping " + previous + ".\nPlaying **" + manager.getCurrentTrack().getInfo().title + "**");

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }

    private static class Pause extends SubCommand {

        public Pause() {
            super("pause",
                    "Pause music in the current server.",
                    CommandUtils.COMMAND_PREFIX + "music pause");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
            builder.setColor(Color.ORANGE);
            builder.setDescription("Pausing");

            manager.setPaused(true);

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }

    private static class Resume extends SubCommand {

        public Resume() {
            super("resume",
                    "Resume music in the current server",
                    CommandUtils.COMMAND_PREFIX + "music resume");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
            builder.setColor(Color.ORANGE);
            builder.setDescription("Resuming");

            manager.setPaused(false);

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }

    private static class Shuffle extends SubCommand {

        public Shuffle() {
            super("shuffle",
                    "Randomize tracks queued.",
                    CommandUtils.COMMAND_PREFIX + "music shuffle");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            msg.getChannel().sendTyping();
            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
            builder.setColor(Color.ORANGE);
            builder.setDescription("Queue randomized !");

            manager.shuffle();

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }

    private static class Clear extends SubCommand {

        public Clear() {
            super("clear",
                    "Clear the queue and disconnect the bot from voice channel in the current server.",
                    CommandUtils.COMMAND_PREFIX + "music clear");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
            builder.setColor(Color.ORANGE);
            builder.setDescription("Clearing queue and disconnecting...");

            manager.stop();
            manager.disconnect();

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }

    private static class Queue extends SubCommand {

        public Queue() {
            super("queue",
                    "Show a list with all queued songs.",
                    CommandUtils.COMMAND_PREFIX + "music queue");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
            builder.setColor(Color.ORANGE);

            StringBuilder sb = new StringBuilder();
            sb.append("Current queue:\n\n");
            List<AudioTrack> queue = manager.getQueue();

            if (!queue.isEmpty()) {
                for (int i = 0; i < manager.getQueue().size(); i++) {
                    sb.append((i + 1) + ". **" + queue.get(i).getInfo().title + "**");
                    sb.append(" [" + MessageUtils.formatTimestamp(queue.get(i).getDuration()) + "]\n");
                }
            } else
                sb.append("No tracks queued ! Add one with `>music play [identifier]`");

            builder.setDescription(sb.toString());
            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }

    private static class Current extends SubCommand {

        private static final String baseUrl = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id={ID}&key=AIzaSyDAPlMGxm7RbG7j01vWA6s2wNkrzzPRbBw";

        public Current() {
            super("current",
                    "Display informations about the current track.",
                    CommandUtils.COMMAND_PREFIX + "music current");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            GuildMusicManager manager = (GuildMusicManager) param;

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setAuthor("Music info", null, "http://www.jesus-crie.com/img/music.png");
            builder.setColor(Color.ORANGE);

            AudioTrack track = manager.getCurrentTrack();

            if (track == null) {
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("No track playing !", msg.getAuthor())).queue();
                return;
            }

            if (track.getSourceManager().getSourceName().equalsIgnoreCase("youtube")) {
                ObjectMapper mapper = new ObjectMapper();
                YoutubeVideo video;
                try {
                    video = mapper.readValue(new URL(baseUrl.replace("{ID}", track.getIdentifier())), new TypeReference<YoutubeVideo>() {});
                } catch (IOException e) {
                    Logger.warning("Failed to load Youtube Video !", LogFrom.COMMAND);
                    video = new YoutubeVideo();
                }

                builder.setThumbnail(video.getIcon());
                builder.setTitle(track.getInfo().title);
                builder.setDescription("By **" + track.getInfo().author + "**\n" +
                        "Source: [**Youtube**](http://youtu.be/" + track.getIdentifier() + ")\n\n" +
                        "Time Played: **" + MessageUtils.formatTimestamp(track.getPosition()) + "** of **" + MessageUtils.formatTimestamp(track.getDuration()) + "**");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Name: **" + track.getInfo().title + "**\n")
                    .append("Author: **" + track.getInfo().author + "**\n")
                    .append("Identifier: **" + track.getInfo().identifier + "**\n")
                    .append("Time played: **" + MessageUtils.formatTimestamp(track.getPosition()) + "** of **" + MessageUtils.formatTimestamp(track.getDuration()) + "**\n")
                    .append("Source Name: **" + track.getSourceManager().getSourceName() + "**");

                builder.setDescription(sb.toString());
            }

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }
}
