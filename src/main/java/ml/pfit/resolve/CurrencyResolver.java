package ml.pfit.resolve;

import ml.pfit.dto.TraceRequest;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CurrencyResolver implements CurrencyResolverInterface {

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
     * @param traceRequest instance to be loaded */
    public void resolve(TraceRequest traceRequest)  {
        try {
            JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + traceRequest.getCurrency());
            // Free plan only allows EUR as base currency.  Otherwise a base_currency_access_restricted is returned
            JSONObject rates =  (JSONObject)response.get("rates");
            double usdRate = (double) rates.get("USD");
            double targetRate = (double) rates.get(traceRequest.getCurrency());
            traceRequest.setCurrencyRateUSD(targetRate / usdRate);
        } catch (Exception e) {
            // API not available, limit reached or information unavailable.
            // Currency conversion not available, ignore.
            traceRequest.setCurrencyRateUSD(0.0);
        }
    }

}
