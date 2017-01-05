package com.jesus_crie.kankanbot.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    private static List<Command> commands = new ArrayList<>();

    public static void registerCommands(Command... cmds) {
        commands.addAll(Arrays.asList(cmds));
    }

    public static List<Command> getCommands() { return commands; }

    public static Command getCommand(String cmdName) {
        for (Command c : commands) {
            if (c.getName().equalsIgnoreCase(cmdName) || c.getAliases().contains(cmdName.toLowerCase()))
                return c;
        }
        return null;
    }

    public static int getHiddens() {
        int i = 0;
        for (Command c : commands)
            if (c.isHidden())
                i++;
        return i;
    }
}
