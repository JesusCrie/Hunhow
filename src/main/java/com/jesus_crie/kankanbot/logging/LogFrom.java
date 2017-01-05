package com.jesus_crie.kankanbot.logging;

public enum LogFrom {

    COMMAND("[Command] "),
    LISTENER("[Listener] "),
    STARTUP("[Startup] "),
    RUNNING("[Running] "),
    MUSIC("[Music] ");

    private String value;
    LogFrom (String v) {
        value = v;
    }
    @Override
    public String toString() { return value; }
}
