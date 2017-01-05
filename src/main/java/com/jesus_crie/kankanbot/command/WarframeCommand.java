package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import net.dv8tion.jda.core.entities.Message;

import java.util.Arrays;

public class WarframeCommand extends Command {

    public WarframeCommand() {
        super("warframe",
                Arrays.asList("wf", "war"),
                "Warframe command",
                CommandUtils.COMMAND_PREFIX + "warframe <alert|invasion>",
                AccessLevel.GODTIER,
                true);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        Logger.info("Executing command warframe", LogFrom.COMMAND);
    }
}
