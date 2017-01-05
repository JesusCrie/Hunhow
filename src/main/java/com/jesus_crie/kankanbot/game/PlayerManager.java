package com.jesus_crie.kankanbot.game;

import net.dv8tion.jda.core.entities.Member;

import java.util.HashMap;

public class PlayerManager {

    private static HashMap<String, Player> players = new HashMap<>();

    public static boolean isPlaying(Member member) {
        if (players.containsKey(member.getUser().getId()))
            if (players.get(member.getUser().getId()).isPlaying())
                return true;
        return false;
    }

    public static Player getPlayer(Member member) {
        if (!players.containsKey(member.getUser().getId()))
            players.put(member.getUser().getId(), new Player(member));
        return players.get(member.getUser().getId());
    }
}
