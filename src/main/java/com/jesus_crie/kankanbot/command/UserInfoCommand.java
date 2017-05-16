package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.permission.Permissions;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.OffsetDateTime;
import java.util.Arrays;

public class UserInfoCommand extends Command {

    public UserInfoCommand() {
        super("userinfo",
                Arrays.asList("useri", "iinfo", "ui"),
                "Display informations on a user on the current server.",
                CommandUtils.COMMAND_PREFIX + "userinfo [@mention]",
                AccessLevel.EVERYONE,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        if (args.length == 0)
            return true;
        else if (!msg.getMentionedUsers().isEmpty() && msg.getMentionedUsers().size() == 1 && args.length == 1)
            return true;
        else
            return false;
    }

    @Override
    public void execute(Message msg, String[] args) {
        Logger.info("Executing command userinfo", LogFrom.COMMAND);
        Member member = null;
        if (args.length == 1)
            member = msg.getGuild().getMember(msg.getMentionedUsers().get(0));
        else
            member = msg.getGuild().getMember(msg.getAuthor());

        TextChannel channel = (TextChannel) msg.getChannel();

        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setColor(member.getColor());
        builder.setAuthor(member.getUser().getName(), null, member.getUser().getAvatarUrl());
        builder.setThumbnail(member.getUser().getAvatarUrl());

        builder.addField("ID", member.getUser().getId(), true);
        builder.addField("Discriminator", member.getUser().getDiscriminator(), true);
        builder.addField("Status", member.getOnlineStatus().toString(), true);
        builder.addField("Game", member.getGame() != null ? member.getGame().getName() : "None", true);

        OffsetDateTime date =  member.getJoinDate();
        String dateS = date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + " " + date.getHour() + ":" + date.getMinute();
        builder.addField("Join at", dateS, true);
        builder.addField("Access Level", Permissions.getUserAccess(member).toString(), true);

        if (!member.getRoles().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Role role : member.getRoles()) {
                sb.append(role.getName());
                if (member.getRoles().indexOf(role) + 1 != member.getRoles().size())
                    sb.append(", ");
            }
            builder.addField("Roles", sb.toString(), false);
        } else
            builder.addField("Roles", "None", false);

        channel.sendMessage(builder.build()).queue();
    }
}
