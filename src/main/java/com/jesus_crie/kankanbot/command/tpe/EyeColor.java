package com.jesus_crie.kankanbot.command.tpe;

public enum EyeColor {
    BROWN("BROWN"),
    BLUE("BLUE"),
    GREEN("GREEN");

    private String name;

    EyeColor(String n) {
        name = n;
    }

    @Override
    public String toString() {
        return name;
    }
}
