package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;

public class AddCommand extends Command {

    public AddCommand() {
        super("add",
                "Get the invite link to add me on your server.",
                CommandUtils.COMMAND_PREFIX + "add",
                AccessLevel.EVERYONE,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setColor(Color.WHITE);
        builder.setTitle("Invit link");
        builder.setDescription("Click [here](https://discordapp.com/oauth2/authorize?client_id=218742991748071424&scope=bot) to add me in your server !");

        msg.getChannel().sendMessage(builder.build()).queue();
    }
}
