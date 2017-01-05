package com.jesus_crie.kankanbot.game;

import net.dv8tion.jda.core.entities.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {

    protected String name;
    protected String description;
    protected int maxPlayer;

    protected List<Player> players = new ArrayList<>();

    public Game(String name, String description, int maxPlayer) {
        this.name = name;
        this.description = description;
        this.maxPlayer = maxPlayer;
    }

    public abstract void onMessage(Message msg);
}
