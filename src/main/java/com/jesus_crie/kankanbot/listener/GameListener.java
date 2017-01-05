package com.jesus_crie.kankanbot.listener;

import com.jesus_crie.kankanbot.HunhowBot;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GameListener extends ListenerAdapter {

    private HunhowBot bot;
    public GameListener(HunhowBot b) {
        bot = b;
        Logger.info("  - Game Listener successfuly registered", LogFrom.LISTENER);
    }
}
