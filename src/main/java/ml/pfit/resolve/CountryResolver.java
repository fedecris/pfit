package ml.pfit.resolve;

import ml.pfit.cache.Cache;
import ml.pfit.cache.CacheManager;
import ml.pfit.model.Country;
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

    @Autowired
    RemoteAPI remoteAPI;

    @Value("${country.api.baseurl}")
    private String API_BASE_URL;

    @Autowired
    private CacheManager cache;

    /**
     * Retrieves additional information about a country based on its code
     * @param country instance to be loaded */
    public void resolve(Country country) throws Exception {
        // Country information does not change in time, check for its existence on cache.
        // Cache behaviour: Country Code -> Country
        Country cached = (Country)getCache().get(country.getCode());
        if (cached!=null) {
            country.load(cached);
            return;
        }
        JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + country.getCode());
        country.setCurrency(getCurrency(response));
        country.setLanguages(getLanguages(response));
        country.setTimeZones(getTimeZones(response));
        country.setDistance(getDistance(response));
        getCache().put(country.getCode(), country);
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
        return cache.getInstance("country");
    }

}
