package com.jesus_crie.kankanbot.warframe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class NameMiddleware {

    private static HashMap<String, String> names = new HashMap<>();

    public static void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            names = mapper.readValue(new URL("http://www.jesus-crie.com/warframe/items.json"), new TypeReference<HashMap<String, String>>() {});
            Logger.info("Names loaded !", LogFrom.WARFRAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String query) {
        return names.getOrDefault(query, query);
    }
}
