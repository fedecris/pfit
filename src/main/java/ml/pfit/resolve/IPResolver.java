package ml.pfit.resolve;

import ml.pfit.cache.Cache;
import ml.pfit.cache.CacheManager;
import ml.pfit.dto.TraceRequest;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IPResolver {

    @Value("${ip.api.baseurl}")
    private String API_BASE_URL;

    private final RemoteAPI remoteAPI;

    private final CacheManager cacheManager;

    @Autowired
    public IPResolver(RemoteAPI remoteAPI, CacheManager cacheManager) {
        this.remoteAPI = remoteAPI;
        this.cacheManager = cacheManager;
    }

    /**
     * Retrieves information about a country based on its IP
     * @param traceRequest the request to solve
     */
    public void resolve(TraceRequest traceRequest) throws Exception {
        // Retrieve IP info
        JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL.replace("IP_PLACEHOLDER", traceRequest.getIp()));
        traceRequest.setCode((String)response.get("country_code"));
        traceRequest.setName((String)response.get("country_name"));
    }

    /** Retrieves the IP cache */
    protected Cache getCache() {
        return cacheManager.getInstance("ip");
    }

}
