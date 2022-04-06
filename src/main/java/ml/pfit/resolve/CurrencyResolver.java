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
public class CurrencyResolver {

    @Value("${currency.api.baseurl}")
    private String API_BASE_URL;

    @Value("${currency.api.cachemins}")
    private Integer API_CACHE_EXP_MINS;

    private final RemoteAPI remoteAPI;

    private final CacheManager cacheManager;

    @Autowired
    public CurrencyResolver(RemoteAPI remoteAPI, CacheManager cacheManager) {
        this.remoteAPI = remoteAPI;
        this.cacheManager = cacheManager;
    }

    /**
     * Retrieves currency rate information about a country based on its main currency.
     * If no info is available, will set 0.0 as currency rate
     * @param country instance to be loaded */
    public void resolve(Country country)  {
        try {
            // Currency information might change in time, check for its existence on a short-lived cache.
            // Cache behaviour: Country Code -> USDCurrencyRate
            Double cached = (Double)getCache().get(country.getCode());
            if (cached!=null) {
                country.setCurrencyRateUSD(cached);
                return;
            }
            JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + country.getCurrency());
            // Free plan only allows EUR as base currency.  Otherwise a base_currency_access_restricted is returned
            JSONObject rates =  (JSONObject)response.get("rates");
            double usdRate = (double) rates.get("USD");
            double targetRate = (double) rates.get(country.getCurrency());
            country.setCurrencyRateUSD(targetRate / usdRate);
            getCache().put(country.getCode(), country.getCurrencyRateUSD());
        } catch (Exception e) {
            // API not available, limit reached or information unavailable.
            // Currency conversion not available, ignore.
            country.setCurrencyRateUSD(0.0);
        }
    }

    /** Retrieves the currency cache */
    protected Cache getCache() {
        return cacheManager.getInstance("currency", API_CACHE_EXP_MINS
        );
    }
}
