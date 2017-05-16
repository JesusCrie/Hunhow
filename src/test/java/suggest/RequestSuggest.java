package suggest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RequestSuggest {

    public static void main(String[] args) {
        try {
            HttpResponse<JsonNode> names = Unirest.get("http://www.jesus-crie.com/test.json").asJson();
            System.out.println(names.getBody());

            HttpResponse<JsonNode> node = Unirest.post("http://www.jesus-crie.com/warframe/suggest.php")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(names.getBody())
                    .asJson();
            System.out.println(node.getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
