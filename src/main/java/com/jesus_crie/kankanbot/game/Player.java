package com.jesus_crie.kankanbot.game;

import net.dv8tion.jda.core.entities.Member;

public class Player {

    private Member member;
    private GameName currentGame;

    public Player(Member member) {
        this.member = member;
    }

    public boolean isPlaying() {
        if (currentGame == null)
            return false;
        return true;
    }
}
