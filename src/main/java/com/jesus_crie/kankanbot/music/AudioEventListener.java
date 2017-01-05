package com.jesus_crie.kankanbot.music;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AudioEventListener extends AudioEventAdapter {

    private final AudioPlayer player;
    private final LinkedList<AudioTrack> queue;
    private final List<AudioTrack> auto;
    private final GuildMusicManager manager;

    public AudioEventListener(AudioPlayer player, List<AudioTrack> auto, GuildMusicManager manager) {
        this.player = player;
        queue = new LinkedList<>();
        this.auto = auto;
        this.manager = manager;
        if (!auto.isEmpty())
            queue.add(getRandomAutoTrack());
    }

    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public void nextTrack() {
        Logger.info("[" + manager.getGuild().getName() + "] Starting next track", LogFrom.MUSIC);
        if (queue.isEmpty()) {
            queue.add(getRandomAutoTrack());
        }
        new Thread(() -> {
            if (!player.startTrack(queue.poll().makeClone(), false))
                nextTrack();
        }).start();
        checkPlayer();
    }

    public void skip() {
        player.stopTrack();
        nextTrack();
    }

    public void shuffle() {
        Collections.shuffle(queue);
    }

    public void stop() {
        player.stopTrack();
        queue.clear();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason reason) {
        Logger.info("[" + manager.getGuild().getName() + "] End track whith reason " + reason.toString(), LogFrom.MUSIC);
        if (reason.mayStartNext)
            nextTrack();
    }

    private AudioTrack getRandomAutoTrack() {
        return auto.get(ThreadLocalRandom.current().nextInt(0, auto.size()));
    }

    private void checkPlayer() {
        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Logger.info("[" + manager.getGuild().getName() + "] Checking player...", LogFrom.MUSIC);
                if (player.provideDirectly() == null && !player.isPaused()) {
                    Logger.warning("[" + manager.getGuild().getName() + "] Player check positive, skipping...", LogFrom.MUSIC);
                    skip();
                }
            }
        }).start();
    }
}
