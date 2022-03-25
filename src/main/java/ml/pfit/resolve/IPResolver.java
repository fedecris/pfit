package ml.pfit.resolve;

import ml.pfit.cache.Cache;
import ml.pfit.cache.CacheManager;
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

    @Autowired
    private CacheManager cache;

    /**
     * Retrieves information about a country based on its IP
     * @param ip the IP to look for
     * @return a country instance with basic information like code and name */
    public Country resolve(String ip) throws Exception {
        Country country = new Country();
        // IP-Country association does not change in time.  Check IP existence on cache.
        // Cache behaviour: IP -> Country
        Country cached = (Country)getCache().get(ip);
        if (cached!=null) {
            country.load(cached);
            return country;
        }
        // Retrieve IP info
        JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + ip);
        country.setCode((String)response.get("countryCode3"));
        country.setName((String)response.get("countryName"));
        getCache().put(ip, country);
        return country;
    }

    /** Retrieves the IP cache */
    protected Cache getCache() {
        return cache.getInstance("ip", 1);
    }

}
