package ml.pfit.resolve;

import ml.pfit.cache.Cache;
import ml.pfit.cache.CacheManager;
import ml.pfit.dto.TraceRequest;
import ml.pfit.utils.Distance;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CountryResolver {

    /** Buenos Aires City Latitude */
    static final double BA_LAT = -34.6037f;
    /** Buenos Aires City Longitude */
    static final double BA_LNG = -58.3816f;

    @Value("${country.api.baseurl}")
    private String API_BASE_URL;

    private final RemoteAPI remoteAPI;

    private final CacheManager cacheManager;

    @Autowired
    public CountryResolver(RemoteAPI remoteAPI, CacheManager cacheManager) {
        this.remoteAPI = remoteAPI;
        this.cacheManager = cacheManager;
    }

    /**
     * Retrieves additional information about a country based on its code
     * @param traceRequest instance to be loaded */
    public void resolve(TraceRequest traceRequest) throws Exception {
        JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + traceRequest.getCode());
        traceRequest.setCurrency(getCurrency(response));
        traceRequest.setLanguages(getLanguages(response));
        traceRequest.setTimeZones(getTimeZones(response));
        traceRequest.setDistance(getDistance(response));
        traceRequest.calculateLocalTimes();
    }

    /** @return the main currency of the country */
    protected String getCurrency(JSONObject obj) {
        JSONArray currencies = (JSONArray)obj.get("currencies");
        return (String)((JSONObject)currencies.get(0)).get("code");
    }

    /** @return the list of languages of the country */
    protected ArrayList<String> getLanguages(JSONObject obj) {
        ArrayList<String> ret = new ArrayList<>();
        for (JSONObject jsonObject : (Iterable<JSONObject>) obj.get("languages")) {
            ret.add((String) jsonObject.get("name"));
        }
        return ret;
    }

    /** @return the list of languages of the country */
    protected ArrayList<String> getTimeZones(JSONObject obj) {
        ArrayList<String> ret = new ArrayList<>();
        JSONArray timezones =  (JSONArray)obj.get("timezones");
        for (Object timezone : timezones) {
            ret.add((String) timezone);
        }
        return ret;
    }

    /** @return the distance in KM between the country and Buenos Aires City */
    protected Integer getDistance(JSONObject obj) {
        double lat = (double)((JSONArray)obj.get("latlng")).get(0);
        double lng = (double)((JSONArray)obj.get("latlng")).get(1);
        return Distance.euclidean(lat, BA_LAT, lng, BA_LNG);
    }

    /** Retrieves the country cache */
    protected Cache getCache() {
        return cacheManager.getInstance("country");
    }

}
