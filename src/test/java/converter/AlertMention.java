package converter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertMention {

    private List<String> groups;
    private String image;
    private String keyword;

    @JsonCreator
    public AlertMention(
            @JsonProperty("keyword") String key,
            @JsonProperty("image") String img,
            @JsonProperty("group") List<String> gps) {
        groups = gps;
        image = img;
        keyword = key;
    }

    @Override
    public String toString() {
        return "[" + keyword + ", " + groups.toString() + ", " + image + "]";
    }

    public List<String> getGroups() {
        return groups;
    }

    public String getImage() {
        return image;
    }

    public String getKeyword() {
        return keyword;
    }
}
