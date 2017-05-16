package com.jesus_crie.kankanbot.music;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;

import java.io.IOException;
import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeVideo {

    private static final String baseUrl = "https://www.googleapis.com/youtube/v3/channels?part=snippet&id={USER}&key=AIzaSyDAPlMGxm7RbG7j01vWA6s2wNkrzzPRbBw";

    private YoutubeChannel channel;

    @JsonCreator
    public YoutubeVideo(@JsonProperty("items") JsonNode node) {
        ObjectMapper mapper = new ObjectMapper();
        String id = node.get(0).get("snippet").get("channelId").asText();
        try {
            channel = mapper.readValue(new URL(baseUrl.replace("{USER}", id)), new TypeReference<YoutubeChannel>() {});
        } catch (IOException e) {
            channel = new YoutubeChannel();
            Logger.warning("Failed to load youtube channel !", LogFrom.MUSIC);
        }
    }

    @JsonIgnore
    public YoutubeVideo() {
        channel = new YoutubeChannel();
    }

    @JsonIgnore
    public String getIcon() {
        return channel.getIcon();
    }
}
