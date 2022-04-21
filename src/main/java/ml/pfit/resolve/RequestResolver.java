package ml.pfit.resolve;

import ml.pfit.model.IPAddr;
import ml.pfit.dto.TraceRequestDTO;
import ml.pfit.mapper.CountryRequestMapper;
import ml.pfit.mapper.CurrencyRequestMapper;
import ml.pfit.mapper.IPRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RequestResolver {

    private static final Logger Log = LoggerFactory.getLogger(RequestResolver.class);

    private final IPResolverInterface ipResolver;

    private final CountryResolverInterface countryResolver;

    private final CurrencyResolverInterface currencyResolver;

    @Autowired
    public RequestResolver(IPResolverInterface ipResolver, CountryResolverInterface countryResolver, CurrencyResolverInterface currencyResolver) {
        this.ipResolver = ipResolver;
        this.countryResolver = countryResolver;
        this.currencyResolver = currencyResolver;
    }

    @Autowired
    IPRequestMapper ipMapper;

    @Autowired
    CountryRequestMapper countryMapper;

    @Autowired
    CurrencyRequestMapper currencyMapper;

    /** Retrieves the complete information of a request
     *  @param traceRequestDTO the request to solve
     *  @return the retrieved information related with the Country */
    public TraceRequestDTO resolve(TraceRequestDTO traceRequestDTO) throws Exception {
        Log.info(String.format("--> Tracing Started! %s", traceRequestDTO));
        // Retrieve info from the APIs
        IPAddr ipr = ipResolver.resolve(traceRequestDTO.getIp());
        ipMapper.map(ipr, traceRequestDTO);
        countryMapper.map(countryResolver.resolve(traceRequestDTO.getCode()), traceRequestDTO);
        currencyMapper.map(currencyResolver.resolve(traceRequestDTO.getCurrency()), traceRequestDTO);
        Log.info(String.format("Tracing Finished! %s", traceRequestDTO));
        return traceRequestDTO;
    }

}
