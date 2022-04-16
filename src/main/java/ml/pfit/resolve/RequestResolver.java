package ml.pfit.resolve;

import ml.pfit.dto.TraceRequest;
import ml.pfit.service.StatsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RequestResolver {

    private final IPResolver ipResolver;

    private final CountryResolver countryResolver;

    private final CurrencyResolver currencyResolver;

    @Autowired
    public RequestResolver(IPResolver ipResolver, CountryResolver countryResolver, CurrencyResolver currencyResolver) {
        this.ipResolver = ipResolver;
        this.countryResolver = countryResolver;
        this.currencyResolver = currencyResolver;
    }

    /** Retrieves the complete information of a request
     *  @param traceRequest the request to solve
     *  @return the retrieved information related with the Country */
    public TraceRequest resolve(TraceRequest traceRequest) throws Exception {
        // Retrieve info from the APIs
        ipResolver.resolve(traceRequest);
        countryResolver.resolve(traceRequest);
        currencyResolver.resolve(traceRequest);
        return traceRequest;
    }

}
