package ml.pfit.resolve;



import ml.pfit.dto.CurrencyRequestDTO;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CurrencyResolver implements CurrencyResolverInterface {

    private static final Logger Log = LoggerFactory.getLogger(CurrencyResolver.class);

    @Value("${currency.api.baseurl}")
    private String API_BASE_URL;

    @Value("${currency.api.cachemins}")
    private Integer API_CACHE_EXP_MINS;

    private final RemoteAPI remoteAPI;

    @Autowired
    public CurrencyResolver(RemoteAPI remoteAPI) {
        this.remoteAPI = remoteAPI;
    }


    /**
     * Retrieves currency rate information about a country based on its main currency.
     * If no info is available, will set 0.0 as currency rate
     */
    @Cacheable(cacheManager = "default.cache", value = "currencies", key="#currencyCode")
    public CurrencyRequestDTO resolve(String currencyCode) {
        CurrencyRequestDTO dto = new CurrencyRequestDTO();
        try {
            Log.info(String.format("Resolving currency %s...", currencyCode));
            JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + currencyCode);
            // Free plan only allows EUR as base currency.  Otherwise a base_currency_access_restricted is returned
            JSONObject rates =  (JSONObject)response.get("rates");
            double usdRate = (double) rates.get("USD");
            double targetRate = (double) rates.get(currencyCode);
            dto.setCurrencyRateUSD(targetRate / usdRate);
        } catch (Exception e) {
            // API not available, limit reached or information unavailable.
            // Currency conversion not available, ignore.
            dto.setCurrencyRateUSD(0.0);
        }
        return dto;
    }

}
