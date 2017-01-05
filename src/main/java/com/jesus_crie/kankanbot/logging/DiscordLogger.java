package com.jesus_crie.kankanbot.logging;

import com.jesus_crie.kankanbot.HunhowBot;
import net.dv8tion.jda.core.entities.TextChannel;

public class DiscordLogger {

    private static TextChannel channel;

    public static void logToPrivateGuild(final String log) {
        new Thread(() -> {
            if (HunhowBot.isReady()) {
                if (channel == null)
                    channel = HunhowBot.getInstance().getJda().getTextChannelById("264771224188485633");
                channel.sendMessage(log).queue();
            }
        }).start();
    }
}
