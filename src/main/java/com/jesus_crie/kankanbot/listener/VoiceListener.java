package com.jesus_crie.kankanbot.listener;

import com.jesus_crie.kankanbot.HunhowBot;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.music.GuildMusicManager;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class VoiceListener extends ListenerAdapter {

    public VoiceListener() {
        Logger.info("  - Voice Listener successfully registered !", LogFrom.LISTENER);
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent e) {
        execute(e.getChannelLeft());
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent e) {
        execute(e.getChannelJoined());
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent e) {
        execute(e.getChannelLeft());
        execute(e.getChannelJoined());
    }

    private void execute(VoiceChannel channel) {
        GuildMusicManager manager = HunhowBot.getInstance().getMusicManager().getGuildManager(channel.getGuild());

        if (manager.isConnected() && channel.getId() == manager.getConnectedChannel().getId()) {
            if (channel.getMembers().size() <= 1) {
                Logger.info("[" + channel.getGuild().getName() + "] Auto Pausing", LogFrom.MUSIC);
                manager.setPaused(true);
            } else if (channel.getMembers().size() == 2) {
                Logger.info("[" + channel.getGuild().getName() + "] Auto Resuming", LogFrom.MUSIC);
                manager.setPaused(false);
            }
        }
    }
}
