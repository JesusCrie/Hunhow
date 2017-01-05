package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.util.Collections;

public class GameCommand extends Command {

    public GameCommand() {
        super("game",
                Collections.singletonList("g"),
                "COMING SOON. Use this to play an awesome game made by me.",
                CommandUtils.COMMAND_PREFIX + "game <coming soon>",
                AccessLevel.GODTIER,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("c4"))
                return true;
            else
                return false;
        } else
            return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        Logger.info("Executing command games", LogFrom.COMMAND);
        if (args.length <= 0) {
            list(msg, args);
            return;
        } else {
            if (args[0].equalsIgnoreCase("c4")) {

            }
        }

    }

    private void list(Message msg, String[] args) {
        Logger.info("Executing command games/list", LogFrom.COMMAND);
        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setColor(Color.BLUE);
        builder.setTitle("Games");
        builder.setDescription("List of available games");
        builder.addField("Connect 4", "You need to connect 4 tiles to win !", true);
        builder.addField("Find the Number", "You need to find a number between 0 and 10.000", true);

        msg.getChannel().sendMessage(builder.build()).queue();
    }

    private void connect4(Message msg, String[] args) {
        Logger.info("Executing command games/c4", LogFrom.COMMAND);
    }
}
