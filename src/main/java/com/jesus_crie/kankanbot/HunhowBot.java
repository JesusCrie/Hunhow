package com.jesus_crie.kankanbot;

import com.jesus_crie.kankanbot.command.*;
import com.jesus_crie.kankanbot.game.GameManager;
import com.jesus_crie.kankanbot.listener.*;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.music.MusicManager;
import com.jesus_crie.kankanbot.permission.Permissions;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.RainbowRank;
import com.jesus_crie.kankanbot.warframe.Warframe;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.List;

public class HunhowBot {

    private static HunhowBot instance;
    private static boolean isReady = false;
    public static final String VERSION = "1.5.1";

    private JDA jda;
    private MusicManager music;
    private GameManager game;
    private Warframe warframe;

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
                new VoiceListener(),
                new PouleyListener(),
                new FloraListener()
        );

        Logger.info("Registering Commands...", LogFrom.STARTUP);
        CommandManager.registerCommands(
                new HelpCommand(),
                new UserInfoCommand(),
                new MusicCommand(),
                new MemeCommand(),
                new QuoteCommand(),
                new GroupCommand(),
                new InfosCommand(),
                new SuggestCommand(),
                new WarframeCommand(),
                new Rule34Command(),
                new AddCommand(),
                new ClearCommand(),
                new StopCommand(),
                new BanCommand(),

                new KickCommand(),
                new RestartCommand(),
                new SpamCommand(),
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

        Logger.info("Initializing Warframe components...", LogFrom.STARTUP);
        warframe = new Warframe();
        warframe.startLoop();

        Logger.info("Ready !", LogFrom.STARTUP);
        jda.getPresence().setGame(new GGame(CommandUtils.COMMAND_PREFIX + "help - v" + VERSION));
        isReady = true;
    }

    public void shutdown(boolean free) {
        isReady = false;
        Logger.info("Ending program...", LogFrom.RUNNING);
        warframe.endLoop();
        Logger.info("Shutting down JDA...", LogFrom.RUNNING);
        jda.shutdown(free);
        Logger.info("Done !", LogFrom.RUNNING);
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
