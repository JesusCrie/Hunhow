package com.jesus_crie.kankanbot.util;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MessageUtils {

    public static MessageEmbed getErrorMessage(String error, User author) {
        Logger.warning("Error Message building...", LogFrom.RUNNING);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Something went wrong...");
        builder.setThumbnail("http://www.jesus-crie.com/img/error.png");
        builder.setFooter("Query made by " + author.getName(), author.getEffectiveAvatarUrl());
        builder.setDescription(error);

        return builder.build();
    }

    public static String formatTimestamp(long timestamp) {
        LocalDateTime time = new Timestamp(timestamp).toLocalDateTime().minusHours(1);
        StringBuilder sb = new StringBuilder();
        sb.append(time.getHour() < 10 ? ("0" + time.getHour()) : time.getHour())
            .append(":" + (time.getMinute() < 10 ? ("0" + time.getMinute()) : time.getMinute()))
            .append(":" + (time.getSecond() < 10 ? ("0" + time.getSecond()) : time.getSecond()));
        return sb.toString();
    }
}
