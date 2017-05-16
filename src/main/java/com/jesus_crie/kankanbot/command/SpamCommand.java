package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.util.CommandUtils;
import net.dv8tion.jda.core.entities.Message;

public class SpamCommand extends Command {

    public SpamCommand() {
        super("spam",
                "Spam a channel with an automatic message",
                CommandUtils.COMMAND_PREFIX + "spam <howMany>",
                AccessLevel.GODTIER,
                true);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        if (args.length >= 1) {
            try {
                int i = Integer.parseInt(args[0]);
                if (i > 0)
                    return true;
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void execute(Message msg, String[] args) {
        int howMany = Integer.parseInt(args[0]);

        for (int i = 0; i < howMany; i++) {
            msg.getChannel().sendMessage("SPAAAAAAM").queue();
        }
    }
}
