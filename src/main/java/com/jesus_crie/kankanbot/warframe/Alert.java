package com.jesus_crie.kankanbot.warframe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Alert {

    private static String credit = "http://www.jesus-crie.com/warframe/credits.png";

    private String id;
    private long activation;
    private long expiry;

    private String missionType;
    private String faction;
    private Node node;
    private int credits;
    private HashMap<String, Integer> items = new HashMap<>();

    @JsonCreator
    public Alert(ObjectNode node) {
        id = node.findValue("$oid").asText();
        activation = node.get("Activation").findValue("$numberLong").asLong();
        expiry = node.get("Expiry").findValue("$numberLong").asLong();

        JsonNode missionInfo = node.get("MissionInfo");
        missionType = NameMiddleware.get(missionInfo.get("missionType").asText());
        faction = NameMiddleware.get(missionInfo.get("faction").asText());
        this.node = Warframe.getInstance().getNodeInfo(missionInfo.get("location").asText());
        credits = missionInfo.get("missionReward").get("credits").asInt();
        if (missionInfo.get("missionReward").has("countedItems")) {
            JsonNode item = missionInfo.get("missionReward").get("countedItems");
            item.forEach(n -> items.put(ItemBeautifier.get(n.get("ItemType").asText()), n.get("ItemCount").asInt()));
        }
        if (missionInfo.get("missionReward").has("items")) {
            items.put(ItemBeautifier.get(missionInfo.get("missionReward").get("items").get(0).asText()), 1);
        }
    }

    public MessageBuilder getEmbedAlert() {
        EmbedBuilder builder = new EmbedBuilder();
        MessageBuilder mBuilder = new MessageBuilder();

        builder.setColor(Color.CYAN);
        builder.setAuthor(node.getName() + " (" + node.getPlanet() + ")", null, PlanetProvider.Planets.forName(node.getPlanet()).getUrl());
        builder.setDescription("Mission: **" + missionType + " (" + faction + ")**");

        if (!isStarted()) {
            LocalDateTime l = getStartTimeLeft();
            builder.setTitle("Start in **" + l.getHour() + "h " + l.getMinute() + "m**");
        } else {
            LocalDateTime l = getTimeLeft();
            builder.setTitle("Ends in **" + l.getHour() + "h " + l.getMinute() + "m**");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("**" + credits + "**x <:credits:276371657206136832>\n");

        builder.setThumbnail(credit);
        if (!items.isEmpty()) {
            items.forEach((item, count) -> sb.append("**" + count + "**x **" + item + "**"));

            items.keySet().forEach(i ->
                Warframe.getInstance().getMentions().forEach(mention -> {
                    if (i.toLowerCase().contains(mention.getKeyword().toLowerCase())) {
                        builder.setThumbnail(mention.getImage());
                        mention.getGroups().forEach(group -> mBuilder.append(AlertRoles.getRoleByName(group).getAsMention()));
                    }
                })
            );
            sb.append("\n");
        }
        builder.addField("Rewards :", sb.toString(), false);

        builder.setFooter(id, null);

        mBuilder.setEmbed(builder.build());
        return mBuilder;
    }

    public String getId() {
        return id;
    }

    public Node getNode() {
        return node;
    }

    public String getMissionType() {
        return missionType;
    }

    public String getFaction() {
        return faction;
    }

    public int getCredits() {
        return credits;
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public boolean isExpired() {
        return (expiry - System.currentTimeMillis() < 0);
    }

    public boolean isStarted() {
        return (activation - System.currentTimeMillis() < 0);
    }

    public LocalDateTime getTimeLeft() {
        Timestamp diff = new Timestamp(expiry - System.currentTimeMillis());
        return diff.toLocalDateTime().minusHours(1);
    }

    public LocalDateTime getStartTimeLeft() {
        Timestamp diff = new Timestamp(activation - System.currentTimeMillis());
        return diff.toLocalDateTime().minusHours(1);
    }
}
