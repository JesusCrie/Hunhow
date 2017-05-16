package com.jesus_crie.kankanbot.warframe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesus_crie.kankanbot.HunhowBot;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Credits: <:credits:276371657206136832>
//Pl: <:platinum:276371950274871296>
//Ducats: <:ducats:276371961830047744>
public class Warframe {

    private static Warframe instance;

    private Thread alertLoop;
    private TextChannel alertChannel;
    private HashMap<String, String> alertHistory = new HashMap<>();
    private List<AlertMention> mentions;

    private Thread sortieLoop;
    private TextChannel sortieChannel;
    private HashMap<String, String> sortieHistory = new HashMap<>();

    private HashMap<String, Node> nodes = new HashMap<>();
    private WorldState worldState;

    public Warframe() {
        instance = this;
        alertChannel = HunhowBot.getInstance().getJda().getTextChannelById("275315265430880256");
        sortieChannel = HunhowBot.getInstance().getJda().getTextChannelById("275315320091181057");
        NameMiddleware.init();
        AlertRoles.init();
        try {
            ObjectMapper mapper = new ObjectMapper();
            nodes = mapper.readValue(new URL("http://jesus-crie.com/warframe/nodenames.json"), new TypeReference<Map<String, Node>>() {});
            Logger.info("Nodes loaded !", LogFrom.WARFRAME);
            mentions = mapper.readValue(new URL("http://www.jesus-crie.com/warframe/mention.json"), new TypeReference<List<AlertMention>>() {});
            Logger.info("Mention loaded !", LogFrom.WARFRAME);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.error("IOException !!", LogFrom.WARFRAME);
        }
        clearChannels();
    }

    public void startLoop() {
        startAlertLoop();
        startSortieLoop();
    }

    public void startAlertLoop() {
        Logger.info("Starting Alert Loop", LogFrom.WARFRAME);
        if (alertLoop == null || !getStatusAlert()) {
            alertLoop = new Thread(() -> {
                while (true) {
                    try {
                        updateValues();
                        submitAlert();
                        Thread.sleep(29000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            alertLoop.start();
        }
    }

    public void startSortieLoop() {
        Logger.info("Starting Sortie Loop", LogFrom.WARFRAME);
        if (sortieLoop == null || !getStatusSortie()) {
            sortieLoop = new Thread(() -> {
                while (true) {
                    try {
                        updateValues();
                        submitSortie();
                        Thread.sleep(179000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            sortieLoop.start();
        }
    }

    public void endLoop() {
        endAlertLoop();
        endSortieLoop();
    }

    public void endAlertLoop() {
        Logger.info("Stopping Alert Loop...", LogFrom.WARFRAME);
        alertLoop.interrupt();
        Logger.info("Stopped Alert Loop !", LogFrom.WARFRAME);
    }

    public void endSortieLoop() {
        Logger.info("Stopping Sortie Loop...", LogFrom.WARFRAME);
        sortieLoop.interrupt();
        Logger.info("Stopped Sortie Loop !", LogFrom.WARFRAME);
    }

    public void submitAlert() {
        alertHistory.keySet().forEach(id -> {
            try {
                if (!worldState.alertExistForId(id))
                    alertChannel.getMessageById(alertHistory.get(id)).block().deleteMessage().block();
            } catch (RateLimitedException e) { Logger.warning("Rate Limit Exception. Retry in " + e.getRetryAfter(), LogFrom.WARFRAME); }
        });

        worldState.getAlerts().forEach(alert -> {
            if (alertHistory.containsKey(alert.getId()) && alert.isExpired())
                alertChannel.getMessageById(alertHistory.get(alert.getId()))
                        .queue(msg -> msg.deleteMessage().queue());
            else if (alertHistory.containsKey(alert.getId()) && !alert.isExpired())
                alertChannel.getMessageById(alertHistory.get(alert.getId()))
                        .queue(msg -> msg.editMessage(alert.getEmbedAlert().build()).queue());
            else
                alertChannel.sendMessage(alert.getEmbedAlert().build()).queue();
        });
    }

    public void submitSortie() {
        sortieHistory.keySet().forEach(id -> {
            try {
                if (!worldState.sortieExistForId(id))
                    sortieChannel.getMessageById(sortieHistory.get(id)).block().deleteMessage().block();
            } catch (RateLimitedException e) { Logger.warning("Rate Limit Exception. Retry in " + e.getRetryAfter(), LogFrom.WARFRAME); }
        });

        worldState.getSorties().forEach(sortie -> {
            if (sortieHistory.containsKey(sortie.getId()) && sortie.isExpired())
                sortieChannel.getMessageById(sortieHistory.get(sortie.getId()))
                    .queue(msg -> msg.deleteMessage().queue());
            else if (sortieHistory.containsKey(sortie.getId()) && !sortie.isExpired())
                sortieChannel.getMessageById(sortieHistory.get(sortie.getId()))
                    .queue(msg -> msg.editMessage(sortie.getEmbedSortie().build()).queue());
            else
                sortieChannel.sendMessage(sortie.getEmbedSortie().build()).queue();
        });
    }

    public void updateValues() {
        try {
            //Update History
            alertHistory.clear();
            alertChannel.getHistory().retrievePast(100).block().forEach(msg ->
                    alertHistory.put(msg.getEmbeds().get(0).getFooter().getText(), msg.getId())
            );

            sortieHistory.clear();
            sortieChannel.getHistory().retrievePast(100).block().forEach(msg ->
                sortieHistory.put(msg.getEmbeds().get(0).getFooter().getText(), msg.getId())
            );
        } catch (RateLimitedException e) {
            Logger.warning("Got ratelimited... Retry after " + e.getRetryAfter(), LogFrom.WARFRAME);
        }

        //Update WorldState
        try {
            ObjectMapper mapper = new ObjectMapper();
            worldState = mapper.readValue(new URL("http://content.warframe.com/dynamic/worldState.php"), new TypeReference<WorldState>() {});
        } catch (IOException e) {
            e.printStackTrace();
            Logger.error("IOException on update", LogFrom.WARFRAME);
        }
    }

    public void clearChannels() {
        //alertChannel.getHistory().retrievePast(100).queue(history -> history.forEach(m -> m.deleteMessage().queue()));
        //sortieChannel.getHistory().retrievePast(100).queue(history -> history.forEach(m -> m.deleteMessage().queue()));
        try {
            alertChannel.getHistory().retrievePast(100).block().forEach(m -> {
                try {
                    m.deleteMessage().block();
                } catch (RateLimitedException e) { Logger.warning("Got ratelimited lol", LogFrom.WARFRAME); }
            });
            sortieChannel.getHistory().retrievePast(100).block().forEach(m -> {
                try {
                    m.deleteMessage().block();
                } catch (RateLimitedException e) { Logger.warning("Got ratelimited lol", LogFrom.WARFRAME); }
            });
        } catch (RateLimitedException e) {}
    }

    public Node getNodeInfo(String query) {
        return nodes.getOrDefault(query, new Node("Unknow", query, "Unknow", "Unknow"));
    }

    public void reloadNames() {
        NameMiddleware.init();
        AlertRoles.init();
        try {
            ObjectMapper mapper = new ObjectMapper();
            nodes = mapper.readValue(new URL("http://jesus-crie.com/warframe/nodenames.json"), new TypeReference<Map<String, Node>>() {});
            Logger.info("Nodes loaded !", LogFrom.WARFRAME);
            mentions = mapper.readValue(new URL("http://www.jesus-crie.com/warframe/mention.json"), new TypeReference<List<AlertMention>>() {});
            Logger.info("Mention loaded !", LogFrom.WARFRAME);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.error("IOException !!", LogFrom.WARFRAME);
        }
    }

    public boolean getStatusAlert() {
        return (alertLoop.isInterrupted() || alertLoop.isAlive());
    }

    public boolean getStatusSortie() {
        return (sortieLoop.isInterrupted() || sortieLoop.isAlive());
    }

    public WorldState getWorldState() {
        return worldState;
    }

    public List<AlertMention> getMentions() {
        return mentions;
    }

    public static Warframe getInstance() {
        return instance;
    }
}
