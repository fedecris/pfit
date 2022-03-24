package ml.pfit.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component
public class RemoteAPI {

    /** Connects to an API end-point
     *  @return an already parsed object
     *  @throws Exception in case of an error */
    public Object call(String url) throws Exception {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() != 200)
                throw new Exception("Status code != 200");
            String respStr = EntityUtils.toString(response.getEntity());
            JSONParser jsonParser = new JSONParser();
            return jsonParser.parse(respStr);
        } catch (Exception e) {
            throw new Exception(String.format("Error connecting to %s: %s", url, e));
        }
    }
}
