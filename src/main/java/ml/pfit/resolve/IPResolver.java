package ml.pfit.resolve;

import ml.pfit.dto.IPRequestDTO;
import ml.pfit.dto.TraceRequestDTO;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class IPResolver implements IPResolverInterface {

    @Value("${ip.api.baseurl}")
    private String API_BASE_URL;

    private final RemoteAPI remoteAPI;

    @Autowired
    public IPResolver(RemoteAPI remoteAPI) {
        this.remoteAPI = remoteAPI;
    }

    @Autowired
    IPRequestDTO dto;

    /**
     * Retrieves information about a country based on its IP
     */
    @Cacheable(cacheManager="default.cache", key="#ip", value="IPRequestDTO")
    public IPRequestDTO resolve(String ip) throws Exception {
        // Retrieve IP info
        JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL.replace("IP_PLACEHOLDER", ip));
        dto.setCode((String)response.get("country_code"));
        dto.setName((String)response.get("country_name"));
        return dto;
    }

}
