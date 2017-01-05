package com.jesus_crie.kankanbot.util;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class MessageUtils {

    public static MessageEmbed getErrorMessage(String error, User author) {
        Logger.info("Error Message building...", LogFrom.RUNNING);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Something went wrong...");
        builder.setThumbnail("http://www.jesus-crie.com/img/error.png");
        builder.setFooter("Query made by " + author.getName(), author.getEffectiveAvatarUrl());
        builder.setDescription(error);

        return builder.build();
    }
}
