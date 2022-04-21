package ml.pfit.resolve;

import ml.pfit.model.IPAddr;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class IPResolver implements IPResolverInterface {

    private static final Logger Log = LoggerFactory.getLogger(IPResolver.class);

    @Value("${ip.api.baseurl}")
    private String API_BASE_URL;

    private final RemoteAPI remoteAPI;

    @Autowired
    public IPResolver(RemoteAPI remoteAPI) {
        this.remoteAPI = remoteAPI;
    }

    /**
     * Retrieves information about a country based on its IP
     */
    @Cacheable(cacheManager = "default.cache", value = "ips", key="#ip")
    public IPAddr resolve(String ip) throws Exception {
        Log.info(String.format("Resolving IP %s...", ip));
        // Retrieve IP info
        JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL.replace("IP_PLACEHOLDER", ip));
        IPAddr dto = new IPAddr();
        dto.setCode((String)response.get("country_code"));
        dto.setName((String)response.get("country_name"));
        return dto;
    }

}
