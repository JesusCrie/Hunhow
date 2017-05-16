package com.jesus_crie.kankanbot.util;

import com.jesus_crie.kankanbot.HunhowBot;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.util.Random;

public class RainbowRank {

    private Role role;
    private Thread loop;
    private Random random;

    public RainbowRank(String guild, String roleName) {
        role = HunhowBot.getInstance().getJda().getGuildById(guild).getRolesByName(roleName, true).get(0);
        random = new Random();
    }

    public void enable() {
        loop = new Thread(() -> {
            while (true) {
                try {
                    Color c = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                    role.getManager().setColor(c).queue();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        loop.start();
    }

    public void disable() {
        if (loop != null)
            loop.interrupt();
    }
}
