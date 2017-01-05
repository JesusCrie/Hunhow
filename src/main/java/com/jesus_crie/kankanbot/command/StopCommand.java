package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.HunhowBot;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.util.Arrays;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop",
                Arrays.asList("shutdown", "exit"),
                "Shutdown the bot and disconnect it from Discord.",
                CommandUtils.COMMAND_PREFIX + "stop",
                AccessLevel.GODTIER,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) { return true; }

    @Override
    public void execute(Message msg, String[] args) {
        Logger.info("Executing command stop", LogFrom.COMMAND);
        TextChannel channel = (TextChannel) msg.getChannel();

        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setTitle("Shutting down...");
        builder.setColor(Color.WHITE);

        channel.sendMessage(builder.build()).queue();

        HunhowBot.getInstance().getMusicManager().disconnectFromAll();
        HunhowBot.getInstance().shutdown(true);
    }
}
