package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import net.dv8tion.jda.core.entities.Message;

public class TestCommand extends Command {

    public TestCommand() {
        super("test",
                "Just a test command.",
                CommandUtils.COMMAND_PREFIX + "test",
                AccessLevel.GODTIER,
                true);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        Logger.info("Executing command test", LogFrom.COMMAND);
        /*Icon icon = null;
        try {
             icon = Icon.from(new FileInputStream(new File(Main.class.getClassLoader().getResource("riven.png").getFile())));
             Logger.info("Avatar loaded", LogFrom.COMMAND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HunhowBot.getInstance().getJda().getSelfUser().getManager().setAvatar(icon).queue(aAvoid -> {
            Logger.info("Avatar changed ! " + aAvoid.toString(), LogFrom.COMMAND);
        });*/
    }
}
