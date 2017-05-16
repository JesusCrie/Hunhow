import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Alert {

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
        id = node.findValue("$id").asText();
        activation = node.get("Activation").get("sec").asLong();
        expiry = node.get("Expiry").get("sec").asLong();
        activation *= 1000;
        expiry *= 1000;

        JsonNode missionInfo = node.get("MissionInfo");
        missionType = missionInfo.get("missionType").asText();
        faction = missionInfo.get("faction").asText();
        this.node = Tester.getNode(missionInfo.get("location").asText());
        credits = missionInfo.get("missionReward").get("credits").asInt();
        if (missionInfo.get("missionReward").has("countedItems")) {
            JsonNode item = missionInfo.get("missionReward").get("countedItems");
            item.forEach(n -> items.put(n.get("ItemType").asText(), n.get("ItemCount").asInt()));
        }
        if (missionInfo.get("missionReward").has("items")) {
            items.put(missionInfo.get("missionReward").get("items").get(0).asText(), 1);
        }
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(node.getName() + " (" + node.getPlanet() + ")\n");
        if (isStarted()) {
            LocalDateTime left = getTimeLeft();
            sb.append("Time Left: " + left.getHour() + "h " + left.getMinute() + "m\n");
        } else {
            LocalDateTime left = getStartTimeLeft();
            sb.append("Start in: " + left.getHour() + "h " + left.getMinute() + "m\n");
        }
        sb.append("Credits: " + credits + "\n");
        if (!items.isEmpty())
            items.forEach((item, count) -> sb.append(count + "x " + item + "\n"));
        sb.append("\n");
        return sb.toString();
    }
}
