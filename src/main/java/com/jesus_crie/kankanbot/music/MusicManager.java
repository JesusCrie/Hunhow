package com.jesus_crie.kankanbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.entities.Guild;

import java.util.HashMap;
import java.util.List;

public class MusicManager {

    private HashMap<String, GuildMusicManager> guildManagers;
    private AudioPlayerManager playerManager;
    private AutoPlaylist autoPlaylist;

    public MusicManager() {
        guildManagers = new HashMap<>();
        playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerLocalSource(playerManager);
        AudioSourceManagers.registerRemoteSources(playerManager);

        autoPlaylist = new AutoPlaylist("PL_aqGRnZqeg5l8f8P61SboaCweuSapHEk", playerManager);
        while (!autoPlaylist.isReady()) {}
    }

    public void registerGuilds(List<Guild> guilds) {
        for (Guild g : guilds)
            guildManagers.put(g.getId(), new GuildMusicManager(g, playerManager.createPlayer(), autoPlaylist));
    }

    public GuildMusicManager getGuildManager(Guild guild) {
        GuildMusicManager m = guildManagers.get(guild.getId());

        if (m == null) {
            m = new GuildMusicManager(guild, playerManager.createPlayer(), autoPlaylist);
            guildManagers.put(guild.getId(), m);
        }

        return m;
    }

    public void disconnectFromAll() {
        for (GuildMusicManager man : guildManagers.values()) {
            man.disconnect();
        }
    }

    public AudioPlayerManager getPlayerManager() { return playerManager; }
}
