package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.Main;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.util.Arrays;

public class RestartCommand extends Command{

    public RestartCommand() {
        super("restart",
                Arrays.asList("rstart"),
                "Restart the bot. WARNING: It's totally buggy !",
                CommandUtils.COMMAND_PREFIX + "restart",
                AccessLevel.GODTIER,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        Logger.info("Executing command restart", LogFrom.COMMAND);
        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setTitle("Restarting...");
        builder.setColor(Color.WHITE);

        msg.getChannel().sendMessage(builder.build()).queue();

        Main.restart();
    }
}
