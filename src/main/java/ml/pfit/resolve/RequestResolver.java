package ml.pfit.resolve;

import ml.pfit.model.Country;
import ml.pfit.service.StatsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestResolver {

    @Autowired
    IPResolver ipResolver;

    @Autowired
    CountryResolver countryResolver;

    @Autowired
    CurrencyResolver currencyResolver;

    @Autowired
    StatsFactory statsFactory;

    /** Retrieves the complete information of a request
     *  @param ip the IP for this request
     *  @return the retrieved information related with the Country */
    public Country resolve(String ip) throws Exception {
        // Retrieve info from the APIs
        Country country = ipResolver.resolve(ip);
        countryResolver.resolve(country);
        currencyResolver.resolve(country);
        // Store stats
        statsFactory.getInstance().storeRequest(country);
        return country;
    }

}
