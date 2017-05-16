package converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AlertMentionner {

    private static List<AlertMention> mentions;

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mentions = mapper.readValue(new URL("http://www.jesus-crie.com/warframe/mention.json"), new TypeReference<List<AlertMention>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        mentions.forEach(m -> System.out.println(m));
    }
}
