import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tester {

    private static HashMap<String, Node> nodes;

    private static List<Alert> alerts;

    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            //Nodes
            nodes = mapper.readValue(new URL("http://jesus-crie.com/warframe/nodenames.json"), new TypeReference<Map<String, Node>>() {});
            //nodes.forEach((key, val) -> System.out.println(key + " => " + val));

            //Alerts
            /*WorldState state = mapper.readValue(new URL("http://content.warframe.com/dynamic/worldState.php"), WorldState.class);
            state.alerts.forEach(a -> {
                //LocalDateTime left = a.getTimeLeft();
                //System.out.println(a + " || " + left.getHour() + ":" + left.getMinute() + ":" + left.getSecond() + " || " + a.isStarted());
                System.out.println(a);
                System.out.println(a.isStarted());
            });*/

            long start = new Long("1486573140000");

            Timestamp diff = new Timestamp(start - System.currentTimeMillis());
            LocalDateTime l = diff.toLocalDateTime().minusHours(1);

            System.out.println(start);
            System.out.println(System.currentTimeMillis());
            System.out.println(diff.getTime());
            System.out.println(l.getHour() + ":" + l.getMinute() + ":" + l.getSecond());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Node getNode(String name) {
        return nodes.getOrDefault(name, new Node("Unknow", name, "Unknow", "Unknow"));
    }
}
