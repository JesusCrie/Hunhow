package com.jesus_crie.kankanbot.command.tpe;

public enum GeneM {
    M("M"),
    b("b");

    private String name;

    GeneM(String n) {
        name = n;
    }

    @Override
    public String toString() {
        return name;
    }

    public static GeneM parse(String in) {
        if (in.toLowerCase().startsWith("m")) {
            return GeneM.M;
        } else {
            return GeneM.b;
        }
    }

    public static GeneM parse(char c) {
        if (c == 'm') {
            return GeneM.M;
        } else {
            return GeneM.b;
        }
    }
}
