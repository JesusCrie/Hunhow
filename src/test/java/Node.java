import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {

    private String planet;
    private String name;
    private String type;
    private String faction;

    @JsonCreator
    public Node(@JsonProperty("planet") String planet,
                @JsonProperty("name") String name,
                @JsonProperty("type") String type,
                @JsonProperty("faction") String faction) {
        this.planet = planet;
        this.name = name;
        this.type = type;
        this.faction = faction;
    }

    public String getPlanet() {
        return planet;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getFaction() {
        return faction;
    }

    @Override
    public String toString() {
        return "[Planet: " + planet + ", Name: " + name + ", Type: " + type + ", Faction: " + faction + "]";
    }
}
