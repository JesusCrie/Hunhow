package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class KickCommand extends Command {

    public KickCommand() {
        super("kick",
                "Kick someone from this server.",
                CommandUtils.COMMAND_PREFIX + "kick <@mention>",
                AccessLevel.ADMIN,
                true);
    }

    public boolean isValid(Message msg, String[] args) {
        if (args.length >= 1 && msg.getMentionedUsers().size() == 1)
            return true;
        return false;
    }

    public void execute(Message msg, String[] args) {
        Logger.info("Executing command kick", LogFrom.COMMAND);
        User toKick = msg.getMentionedUsers().get(0);
        if (toKick.getId().equalsIgnoreCase("182547138729869314")) {
            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("You can't kick this person !", msg.getAuthor())).queue();
            return;
        }

        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setTitle("KICK HAMMER");
        builder.setDescription(toKick.getName() + " is kicked, finnaly !");
        builder.setColor(Color.WHITE);
        builder.setImage("http://i.imgur.com/O3DHIA5.gif");

        msg.getGuild().getController().kick(msg.getGuild().getMember(toKick)).queue();
        msg.getChannel().sendMessage(builder.build()).queue();
    }
}
