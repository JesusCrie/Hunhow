package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import net.dv8tion.jda.core.entities.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command {

    private String name;
    private List<String> aliases;
    private String description;
    private AccessLevel accessLevel;
    private String syntax;
    private boolean hidden;
    private List<String> guildOnly;
    private List<SubCommand> subCommands;

    public Command(String name, List<String> aliases, String description, String syntax, AccessLevel requiered, boolean hidden) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.accessLevel = requiered;
        this.syntax = syntax;
        this.hidden = hidden;
        subCommands = new ArrayList<>();
        Logger.info("  - " + name.substring(0, 1).toUpperCase() + name.substring(1), LogFrom.COMMAND);
    }

    public Command(String name, List<String> aliases, String description, String syntax, AccessLevel requiered, boolean hidden, List<String> guilds) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.accessLevel = requiered;
        this.syntax = syntax;
        this.hidden = hidden;
        subCommands = new ArrayList<>();
        guildOnly = guilds;
        Logger.info("  - " + name.substring(0, 1).toUpperCase() + name.substring(1), LogFrom.COMMAND);
    }

    public Command(String name, String description, String syntax, AccessLevel requiered, boolean hidden) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.accessLevel = requiered;
        this.aliases = new ArrayList<>();
        this.hidden = hidden;
        subCommands = new ArrayList<>();
        Logger.info("  - " + name.substring(0, 1).toUpperCase() + name.substring(1), LogFrom.COMMAND);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public String getSyntax() {
        return syntax;
    }

    public boolean isHidden() {
        return hidden;
    }

    public List<String> getGuilds() {
        return guildOnly;
    }

    protected void registerSubCommands(SubCommand... subs) {
        subCommands.addAll(Arrays.asList(subs));
    }

    protected List<SubCommand> getSubCommands() {
        return subCommands;
    }

    protected SubCommand getSubCommand(String name) {
        for (SubCommand sub : subCommands) {
            if (sub.getName().equalsIgnoreCase(name))
                return sub;
        }
        return null;
    }

    protected String[] getSubArgs(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }

    public boolean hasSubCommands() {
        return !subCommands.isEmpty();
    }

    public boolean hasAliases() {
        return !aliases.isEmpty();
    }

    public abstract boolean isValid(Message msg, String[] args);

    public abstract void execute(Message msg, String[] args);
}
