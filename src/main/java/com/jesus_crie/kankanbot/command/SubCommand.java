package com.jesus_crie.kankanbot.command;

import net.dv8tion.jda.core.entities.Message;

public abstract class SubCommand {

    private String name;
    private String description;
    private String syntax;

    public SubCommand(String name, String description, String syntax) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public abstract boolean isValid(Message msg, String[] args);

    public abstract void execute(Message msg, String[] args, Object param);
}
