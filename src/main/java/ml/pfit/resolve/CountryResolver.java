package ml.pfit.resolve;


import ml.pfit.dto.CountryRequestDTO;
import ml.pfit.utils.Distance;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CountryResolver implements CountryResolverInterface {

    private static final Logger Log = LoggerFactory.getLogger(CountryResolver.class);

    /** Buenos Aires City Latitude */
    static final double BA_LAT = -34.6037f;
    /** Buenos Aires City Longitude */
    static final double BA_LNG = -58.3816f;

    @Value("${country.api.baseurl}")
    private String API_BASE_URL;

    private final RemoteAPI remoteAPI;

    @Autowired
    public CountryResolver(RemoteAPI remoteAPI) {
        this.remoteAPI = remoteAPI;
    }


    /**
     * Retrieves additional information about a country based on its code
     */
    @Cacheable(cacheManager = "default.cache", value = "countries", key="#countryCode")
    public CountryRequestDTO resolve(String countryCode) throws Exception {
        Log.info(String.format("Resolving countryCode %s...", countryCode));
        JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + countryCode);
        CountryRequestDTO dto = new CountryRequestDTO();
        dto.setCurrency(getCurrency(response));
        dto.setLanguages(getLanguages(response));
        dto.setTimeZones(getTimeZones(response));
        dto.setDistance(getDistance(response));
        return dto;
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


}
