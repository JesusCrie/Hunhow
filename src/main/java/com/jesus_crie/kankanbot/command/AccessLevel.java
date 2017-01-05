package com.jesus_crie.kankanbot.command;

public enum AccessLevel {

    EVERYONE("Everyone (0)", 0),
    MODO("Moderator (1)", 1),
    BOT("Bot (2)", 2),
    ADMIN("Admin (3)", 3),
    GODTIER("Owner (4)", 4);

    private String value;
    private int rank;
    AccessLevel(String v, int r) {
        value = v;
        rank = r;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean isSuperiorThan(AccessLevel other) {
        if (rank >= other.rank)
            return true;
        else
            return false;
    }
}
