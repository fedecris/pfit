package ml.pfit.resolve;

import ml.pfit.model.Country;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IPResolver {

    @Autowired
    private RemoteAPI remoteAPI;

    @Value("${ip.api.baseurl}")
    private String API_BASE_URL;

    /**
     * Retrieves information about a country based on its IP
     * @param ip the IP to look for
     * @return a country instance with basic information like code and name */
    public Country resolve(String ip) throws Exception {
        // TODO: Check cache first
        Country country = new Country();
        JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + ip);
        country.setCode((String)response.get("countryCode3"));
        country.setName((String)response.get("countryName"));
        return country;
    }


}
