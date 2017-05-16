package converter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

public class NodeConvertable {
    private String planet;
    private String name;
    private String type;
    private String faction;
    private String raw;

    public NodeConvertable(String in) {
        String[] split = in.split("\\|");
        planet = split[0];
        name = split[1];
        type = split[2];
        faction = split[3];
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
        return "[Planet: {pl}, Name: {na}, Type: {ty}, Faction: {fa}]"
                .replace("{pl}", planet)
                .replace("{na}", name)
                .replace("{ty}", type)
                .replace("{fa}", faction);
    }

    @JsonSerialize
    @JsonIgnore
    public JsonSerializer<NodeConvertable> getSerializer() {
        JsonSerializer<NodeConvertable> serializer = new JsonSerializer<NodeConvertable>() {
            @Override
            public void serialize(NodeConvertable value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeStartObject();
                gen.writeStringField("planet", planet);
                gen.writeStringField("name", name);
                gen.writeStringField("type", type);
                gen.writeStringField("faction", faction);
                gen.writeEndObject();
                gen.close();
            }
        };

        return serializer;
    }
}
