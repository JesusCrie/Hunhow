package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.HunhowBot;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.SelfUser;

import java.awt.*;
import java.util.Collections;

public class InfosCommand extends Command {

    public InfosCommand() {
        super("infos",
                Collections.singletonList("info"),
                "Display some informations about me, my creator and the ressources used in my developement.",
                CommandUtils.COMMAND_PREFIX + "infos",
                AccessLevel.EVERYONE,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        SelfUser self = HunhowBot.getInstance().getJda().getSelfUser();

        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setAuthor(self.getName(), null, self.getAvatarUrl());
        builder.setColor(Color.WHITE);
        builder.addField("Self infos",
                "Bot User: `" + self.getId() + "/" + self.getName() + "`\n" +
                        "Main Role: `" + msg.getGuild().getMember(self).getRoles().get(0).getName() + "`\n" +
                        "Guilds: `" + HunhowBot.getInstance().getJda().getGuilds().size() + "`",
                false);
        builder.addField("Developement infos",
                "Discord API: [JDA 3 BETA2 108](https://github.com/DV8FromTheWorld/JDA)\n" +
                        "Music Player API: [Lava Player](https://github.com/sedmelluq/lavaplayer/)\n" +
                        "Sources: [Github - Hunhow](https://github.com/JesusCrie/Hunhow)",
                false);
        builder.addField("Creator",
                "Made by <@182547138729869314>\n" +
                        "[Github](https://github.com/JesusCrie)\n" +
                        "I love my creator :heart:",
                false);

        msg.getChannel().sendMessage(builder.build()).queue();
    }
}
