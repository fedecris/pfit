package ml.pfit.resolve;

import lombok.RequiredArgsConstructor;
import ml.pfit.model.IPAddr;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IPResolver implements IPResolverInterface {

    @Value("${ip.api.baseurl}")
    private String API_BASE_URL = "";

    private final RemoteAPI remoteAPI;

    /** Retrieves information about a country based on its IP */
    @Cacheable(cacheManager = "default.cache", value = "ips", key="#ip")
    public IPAddr resolve(String ip) throws Exception {
        JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL.replace("IP_PLACEHOLDER", ip));
        return new IPAddr(ip, (String)response.get("country_code"), (String)response.get("country_name"));
    }

}
