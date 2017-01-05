package com.jesus_crie.kankanbot.command.tpe;

public enum GeneV {
    V("V"),
    b("b");

    private String name;

    GeneV(String n) {
        name = n;
    }

    @Override
    public String toString() {
        return name;
    }

    public static GeneV parse(String in) {
        if (in.toLowerCase().startsWith("v")) {
            return GeneV.V;
        } else {
            return GeneV.b;
        }
    }

    public static GeneV parse(char c) {
        if (c == 'v') {
            return GeneV.V;
        } else {
            return GeneV.b;
        }
    }
}