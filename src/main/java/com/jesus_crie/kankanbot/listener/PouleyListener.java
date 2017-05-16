package com.jesus_crie.kankanbot.listener;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Random;

public class PouleyListener extends ListenerAdapter {

    public Random random;

    public PouleyListener() {
        Logger.info("  - Pouley Listener successfully registered !", LogFrom.LISTENER);
        random = new Random();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().getId().equalsIgnoreCase(e.getJDA().getSelfUser().getId())
                || e.getChannelType() != ChannelType.TEXT
                || !e.getGuild().getId().equalsIgnoreCase("219802541775519744"))
            return;

        new Thread(() -> {
            try {
                if (random.nextInt(100) <= 1) {
                    e.getChannel().sendMessage(e.getAuthor().getAsMention() + ", tu veux du poulay ou pas ?").queue();
                    Thread.sleep(2000);
                    e.getChannel().sendMessage(e.getAuthor().getAsMention() + ", hein dit tu veux du poulay ?").queue();
                }
            } catch (InterruptedException er) {}
        }).start();
    }
}
