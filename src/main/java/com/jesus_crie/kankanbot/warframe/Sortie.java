package com.jesus_crie.kankanbot.warframe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Sortie {

    private String id;
    private long activation;
    private long expiry;

    private String name;
    private SortieMission mission1;
    private SortieMission mission2;
    private SortieMission mission3;

    @JsonCreator
    public Sortie(ObjectNode node) {
        id = node.findValue("Seed").asText();
        activation = node.get("Activation").findValue("$numberLong").asLong();
        expiry = node.get("Expiry").findValue("$numberLong").asLong();
        name = NameMiddleware.get(node.get("Boss").asText());
        activation *= 1000;
        expiry *= 1000;

        mission1 = new SortieMission(node.get("Variants").get(0), 0);
        mission2 = new SortieMission(node.get("Variants").get(1), 1);
        mission3 = new SortieMission(node.get("Variants").get(2), 2);
    }

    public MessageBuilder getEmbedSortie() {
        MessageBuilder mBuilder = new MessageBuilder();
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.RED);

        builder.setAuthor("Defeat " + name, null, "http://vignette3.wikia.nocookie.net/warframe/images/1/15/Sortie_b.png/revision/latest?cb=20151217134250");
        builder.addField(mission1.getEmbedField());
        builder.addField(mission2.getEmbedField());
        builder.addField(mission3.getEmbedField());

        builder.setFooter(id, null);

        mBuilder.setEmbed(builder.build());

        return mBuilder;
    }

    public String getId() {
        return id;
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
}
