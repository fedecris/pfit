package ml.pfit.resolve;

import ml.pfit.model.Country;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrencyResolver {

    @Autowired
    RemoteAPI remoteAPI;

    @Value("${currency.api.baseurl}")
    private String API_BASE_URL;

    /**
     * Retrieves currency rate information about a country based on its main currency.
     * If no info is available, will set 0.0 as currency rate
     * @param country instance to be loaded */
    public void resolve(Country country)  {
        try {
            JSONObject response = (JSONObject) remoteAPI.call(API_BASE_URL + country.getCurrency());
            // Free plan only allows EUR as base currency.  Otherwise a base_currency_access_restricted is returned
            JSONObject rates =  (JSONObject)response.get("rates");
            double usdRate = (double) rates.get("USD");
            double targetRate = (double) rates.get(country.getCurrency());
            country.setCurrencyRateUSD(targetRate / usdRate);
        } catch (Exception e) {
            // API not available, limit reached or information unavailable.
            // Currency conversion not available, ignore.
            country.setCurrencyRateUSD(0.0);
        }
    }
}
