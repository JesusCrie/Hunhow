package com.jesus_crie.kankanbot.listener;

import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Random;

public class FloraListener extends ListenerAdapter {

    public FloraListener() {
        Logger.info("  - Pouley Listener successfully registered !", LogFrom.LISTENER);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (!e.getAuthor().getId().equalsIgnoreCase("146994395340603392") //If it s Flora
                || e.getChannelType() != ChannelType.TEXT
                || !e.getGuild().getId().equalsIgnoreCase("219802541775519744"))
            return;

        if (new Random().nextInt(100) <= 50)
            e.getMessage().addReaction(e.getGuild().getEmotesByName("salt", true).get(0)).queue();
    }
}
