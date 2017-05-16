package com.jesus_crie.kankanbot.warframe;

import com.fasterxml.jackson.databind.JsonNode;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class SortieMission {

    private int index;

    private String missionType;
    private String modifier;
    private Node node;

    public SortieMission(JsonNode n, int index) {
        this.index = index;
        missionType = NameMiddleware.get(n.findValue("missionType").asText());
        modifier = NameMiddleware.get(n.findValue("modifierType").asText());
        node = Warframe.getInstance().getNodeInfo(n.findValue("node").asText());
    }

    public MessageEmbed.Field getEmbedField() {
        String name = "- Mission ";
        switch (index) {
            default:
                name += "1 (Level 50-60)";
                break;
            case 1:
                name += "2 (Level 65-80)";
                break;
            case 2:
                name += "3 (Level 80-100)";
                break;
        }

        StringBuilder content = new StringBuilder();
        content.append("**" + node.getName() + " (" + node.getPlanet() + ")**\n");
        content.append("Mission: **" + missionType + "**\n");
        content.append("Modifier: **" + modifier + "**");

        return new MessageEmbed.Field(name, content.toString(), false);
    }

    public String getMissionType() {
        return missionType;
    }

    public String getModifier() {
        return modifier;
    }

    public Node getNode() {
        return node;
    }
}
