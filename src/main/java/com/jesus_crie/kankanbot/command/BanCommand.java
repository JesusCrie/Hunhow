package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.awt.Color;

public class BanCommand extends Command {

    public BanCommand() {
        super("ban",
                "Ban someone from this server with a cool gif.",
                CommandUtils.COMMAND_PREFIX + "ban <@mention>",
                AccessLevel.GODTIER,
                false);
    }

    public boolean isValid(Message msg, String[] args) {
        if (args.length >= 1 && msg.getMentionedUsers().size() == 1)
            return true;
        return false;
    }

    public void execute(Message msg, String[] args) {
        Logger.info("Executing command ban", LogFrom.COMMAND);
        User toBan = msg.getMentionedUsers().get(0);
        if (toBan.getId().equalsIgnoreCase("182547138729869314")) {
            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("You can't ban this person !", msg.getAuthor())).queue();
            return;
        }

        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setTitle("BAN HAMMER");
        builder.setDescription(toBan + " is BANNED. HELL YEAH !");
        builder.setColor(Color.WHITE);
        builder.setImage("http://i.imgur.com/O3DHIA5.gif");

        msg.getGuild().getController().ban(toBan, 0).queue();
        msg.getChannel().sendMessage(builder.build()).queue();
    }
}
