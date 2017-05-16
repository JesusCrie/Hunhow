package com.jesus_crie.kankanbot.music;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeChannel {

    private String iconUrl;

    @JsonCreator
    public YoutubeChannel(@JsonProperty("items") JsonNode node) {
        iconUrl = node.get(0).get("snippet").get("thumbnails").get("default").get("url").asText();
    }

    @JsonIgnore
    public YoutubeChannel() {
        iconUrl = "http://www.jesus-crie.com/img/question_icon.png";
    }

    @JsonIgnore
    public String getIcon() {
        return iconUrl;
    }
}
