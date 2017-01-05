package com.jesus_crie.kankanbot.game;

public enum GameName {
    CONNECT_FOUR("Connect 4"),
    NUMBER("Number");

    String name;
    GameName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
