package converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class NodeConverter {

    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> ns = mapper.readValue(new URL("http://jesus-crie.com/warframe/nodenames.json"), HashMap.class);
            HashMap<String, NodeConvertable> nodes = new HashMap<>();

            ns.forEach((key, val) -> {
                nodes.put(key, new NodeConvertable(val));
            });

            nodes.forEach((key, val) -> {
                System.out.println(key + " -> " + val);
            });

            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File("./src/test/resources/test.json"), nodes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
