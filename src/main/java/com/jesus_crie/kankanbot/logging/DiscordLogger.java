package com.jesus_crie.kankanbot.logging;

import com.jesus_crie.kankanbot.HunhowBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

public class DiscordLogger {

    private static TextChannel channel;

    public static void logToPrivateGuild(final LogFrom from, final LogLevel lvl, final String log, final String date) {
        new Thread(() -> {
            if (HunhowBot.isReady()) {
                checkChannel();
                //channel.sendMessage("```" + date + lvl.toString() + from + log + "```").queue();
                EmbedBuilder builder = new EmbedBuilder();
                builder.setColor(lvl.toColor());
                builder.setTitle(date + lvl.toString() + from + log);
                channel.sendMessage(builder.build()).queue();
            }
        }).start();
    }

    private static void checkChannel() {
        if (channel == null)
            channel = HunhowBot.getInstance().getJda().getTextChannelById("264771224188485633");
    }
}
