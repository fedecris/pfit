package ml.pfit.resolve;

import ml.pfit.model.Country;
import ml.pfit.service.StatsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestResolver {

    private final IPResolver ipResolver;

    private final CountryResolver countryResolver;

    private final CurrencyResolver currencyResolver;

    private final StatsInterface statsHandler;

    @Autowired
    public RequestResolver(IPResolver ipResolver, CountryResolver countryResolver, CurrencyResolver currencyResolver, StatsInterface statsHandler) {
        this.ipResolver = ipResolver;
        this.countryResolver = countryResolver;
        this.currencyResolver = currencyResolver;
        this.statsHandler = statsHandler;
    }

    /** Retrieves the complete information of a request
     *  @param ip the IP for this request
     *  @return the retrieved information related with the Country */
    public Country resolve(String ip) throws Exception {
        // Retrieve info from the APIs
         Country country = ipResolver.resolve(ip);
        countryResolver.resolve(country);
        currencyResolver.resolve(country);
        // Store stats
        statsHandler.storeRequest(country);
        return country;
    }

}
