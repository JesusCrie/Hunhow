package converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonListCreator {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> hey = new ArrayList<>();
        hey.add("bite");
        hey.add("penis");

        try {
            mapper.writeValue(new File("./test.json"), hey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
