package com.jesus_crie.kankanbot.music;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class AutoPlaylist {

    private List<AudioTrack> autoPlaylist = new ArrayList<>();
    private boolean ready = false;

    public AutoPlaylist(String identifier, AudioPlayerManager manager) {
        Future<Void> task = manager.loadItem(identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                autoPlaylist.add(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack t : playlist.getTracks())
                    autoPlaylist.add(t);
            }

            @Override
            public void noMatches() {
                Logger.error("No mathes with Auto Playlist !", LogFrom.MUSIC);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                Logger.error("Failed to load Auto Playlist !", LogFrom.MUSIC);
            }
        });
        while (task.isDone()) {}
        ready = true;
        Logger.info("Auto Playlist loaded !", LogFrom.MUSIC);
    }

    public List<AudioTrack> get() {
        return autoPlaylist;
    }

    public boolean isReady() {
        return ready;
    }
}
