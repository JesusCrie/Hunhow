package com.jesus_crie.kankanbot.music;

import com.jesus_crie.kankanbot.HunhowBot;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.*;

import java.util.List;

public class GuildMusicManager {

    private Guild guild;
    private AudioPlayer player;
    private AudioEventListener scheduler;
    private AudioPlayerSendHandler sendHandler;

    public GuildMusicManager(Guild guild, AudioPlayer player, AutoPlaylist auto) {
        this.guild = guild;
        this.player = player;
        sendHandler = new AudioPlayerSendHandler(player);

        scheduler = new AudioEventListener(player, auto.get(), this);
        player.addListener(scheduler);
        player.setVolume(15);
        guild.getAudioManager().setSendingHandler(getSendHandler());
    }

    public void connectToChannel(VoiceChannel channel) {
        channel.getGuild().getAudioManager().openAudioConnection(channel);
        while (!isConnected()) {}
        guild.getAudioManager().setSelfDeafened(true);
        skipTrack();
    }

    public void disconnect() {
        if (guild.getAudioManager().isConnected())
            guild.getAudioManager().closeAudioConnection();
    }

    public boolean isConnected() {
        return guild.getAudioManager().isConnected();
    }

    public VoiceChannel getUserChannel(User user) {
        for (VoiceChannel voice : HunhowBot.getInstance().getJda().getVoiceChannels())
            for (Member m : voice.getMembers())
                if (m.getUser().getId().equalsIgnoreCase(user.getId()))
                    return voice;
        return null;
    }

    public VoiceChannel getConnectedChannel() {
        if (!guild.getAudioManager().isConnected())
            return null;
        return guild.getAudioManager().getConnectedChannel();
    }

    public void setVolume(int volume) {
        player.setVolume(volume);
    }

    public int getVolume() {
        return player.getVolume();
    }

    public void play(AudioTrack track) {
        Logger.info("[" + guild.getName() + "] Queuing track", LogFrom.MUSIC);
        if (guild.getAudioManager().isConnected()) {
            scheduler.queue(track);
        }
    }

    public void skipTrack() {
        Logger.info("[" + guild.getName() + "] Skipping track", LogFrom.MUSIC);
        scheduler.nextTrack();
    }

    public void shuffle() {
        Logger.info("[" + guild.getName() + "] Shuffling queue", LogFrom.MUSIC);
        scheduler.shuffle();
    }

    public AudioTrack getCurrentTrack() {
        return player.getPlayingTrack();
    }

    public void setPaused(boolean isPaused) {
        player.setPaused(isPaused);
    }

    public void stop() {
        scheduler.stop();
    }

    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }

    public Guild getGuild() {
        return guild;
    }

    public List<AudioTrack> getQueue() {
        return scheduler.getQueue();
    }
}
