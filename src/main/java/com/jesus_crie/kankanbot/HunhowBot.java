package com.jesus_crie.kankanbot;

import com.jesus_crie.kankanbot.command.*;
import com.jesus_crie.kankanbot.game.GameManager;
import com.jesus_crie.kankanbot.listener.CommandListener;
import com.jesus_crie.kankanbot.listener.GameListener;
import com.jesus_crie.kankanbot.listener.VoiceListener;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.music.MusicManager;
import com.jesus_crie.kankanbot.permission.Permissions;
import com.jesus_crie.kankanbot.util.CommandUtils;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class HunhowBot {

    private static HunhowBot instance;
    private static boolean isReady = false;

    private JDA jda;
    private MusicManager music;
    private GameManager game;

    public HunhowBot(String token) {
        instance = this;
        Logger.info("Attemping to connect...", LogFrom.STARTUP);
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .setAudioEnabled(true)
                    .buildBlocking();
        } catch (LoginException e) {
            Logger.error("Error, Login error while connecting to Discord", LogFrom.STARTUP);
            e.printStackTrace();
            return;
        } catch (RateLimitedException e) {
            Logger.error("Error, JDA send RateLimitException", LogFrom.STARTUP);
            e.printStackTrace();
        } catch (InterruptedException e) {
            Logger.error("Error, InterruptedException", LogFrom.STARTUP);
            e.printStackTrace();
        }
        wakingUp();
    }

    private void wakingUp() {
        Logger.info("Waking up...", LogFrom.STARTUP);
        Logger.info("Conneted as: " + jda.getSelfUser().getId() + "/" + jda.getSelfUser().getName(), LogFrom.STARTUP);
        Logger.info("Connected to:", LogFrom.STARTUP);
        for (Guild g : jda.getGuilds()) {
            Logger.info("  - " + g.getId() + "/" + g.getName(), LogFrom.STARTUP);
        }

        Logger.info("Registering listeners...", LogFrom.STARTUP);
        jda.addEventListener(
                new CommandListener(),
                new GameListener(this),
                new VoiceListener()
        );

        Logger.info("Registering Commands...", LogFrom.STARTUP);
        CommandManager.registerCommands(
                new HelpCommand(),
                new UserInfoCommand(),
                new MusicCommand(),
                //new GameCommand(),
                //new WarframeCommand(),
                new StopCommand(),
                new InfosCommand(),
                new BanCommand(),

                new KickCommand(),
                new RestartCommand(),
                new TpeCommand()
                //new TestCommand()
        );

        Logger.info("Registering Games...", LogFrom.STARTUP);
        GameManager.registerGames(
                //TODO
        );

        Logger.info("Initializing Permissions...", LogFrom.STARTUP);
        Permissions.init(jda.getGuildById("219802541775519744"));

        Logger.info("Initializing music components...", LogFrom.STARTUP);
        music = new MusicManager();
        music.registerGuilds(jda.getGuilds());

        Logger.info("Ready !", LogFrom.STARTUP);
        jda.getPresence().setGame(new GGame(CommandUtils.COMMAND_PREFIX + "help - v1.2.5"));
        getClass().getPackage().getImplementationVersion();
        isReady = true;
    }

    public void shutdown(boolean free) {
        isReady = false;
        Logger.info("Shutting down...", LogFrom.RUNNING);
        jda.shutdown(free);
    }

    public JDA getJda() {
        return jda;
    }

    public MusicManager getMusicManager() {
        return music;
    }

    public static HunhowBot getInstance() {
        return instance;
    }

    public static boolean isReady() {
        return isReady;
    }
}
