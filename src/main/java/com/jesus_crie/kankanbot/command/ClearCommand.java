package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import net.dv8tion.jda.core.entities.Message;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear",
                "Clear some messages from the current channel.",
                CommandUtils.COMMAND_PREFIX + "clear [nmuber of messages]",
                AccessLevel.GODTIER,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        if (args.length >= 1) {
            try {
                Integer.parseInt(args[0]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        int howMany;
        if (args.length >= 1) {
            howMany = Integer.parseInt(args[0]);
            if (howMany >= 1000)
                howMany = 1000;
        } else
            howMany = 1;
        msg.getChannel().getHistory().retrievePast(howMany).queue(history -> {
            history.remove(msg);

            for (Message m : history)
                m.deleteMessage().queue();
        });
        //No message because we don t need it
    }
}
