package com.jesus_crie.kankanbot;

import net.dv8tion.jda.core.entities.Game;

public class GGame implements Game {

    private String game;

    public GGame(String game) {
        this.game = game;
    }

    public GameType getType() {
        return GameType.TWITCH;
    }

    public String getName() {
        return game;
    }

    public String getUrl() {
        return "https://www.twitch.tv/discordapp";
    }
}
