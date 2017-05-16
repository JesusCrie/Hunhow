package com.jesus_crie.kankanbot.logging;

import java.awt.*;

public enum LogLevel {
    INFO(Color.GREEN, "[Info] "),
    WARNING(Color.ORANGE, "[Warning] "),
    ERROR(Color.RED, "[ERROR] ");

    private Color c;
    private String s;
    LogLevel(Color c, String s) {
        this.c = c;
        this.s = s;
    }

    public Color toColor() {
        return c;
    }

    @Override
    public String toString() {
        return s;
    }
}
