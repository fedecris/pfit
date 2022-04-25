package ml.pfit.resolve;

import lombok.RequiredArgsConstructor;
import ml.pfit.model.Currency;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyResolver implements CurrencyResolverInterface {

    @Value("${currency.api.baseurl}")
    private String API_BASE_URL = "";

    @Value("${currency.api.cachemins}")
    private Integer API_CACHE_EXP_MINS;

    private final RemoteAPI remoteAPI;

    /** Retrieves currency rate information about a country based on its main currency.
     * If no info is available, will set 0.0 as currency rate */
    @Cacheable(cacheManager = "default.cache", value = "currencies", key="#currencyCode")
    public Currency resolve(String currencyCode) {
        Currency currency = new Currency("ARS");
        try {
            JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + currencyCode);
            // Free plan only allows EUR as base currency.  Otherwise a base_currency_access_restricted is returned
            JSONObject rates =  (JSONObject)response.get("rates");
            double usdRate = (double) rates.get("USD");
            double targetRate = (double) rates.get(currencyCode);
            currency.setCurrencyRateUSD(targetRate / usdRate);
        } catch (Exception e) {
            // API not available, limit reached or information unavailable.
            // Currency conversion not available, ignore.
            currency.setCurrencyRateUSD(0.0);
        }
        return currency;
    }

}
