package ml.pfit.resolve;

import ml.pfit.dto.IPRequestDTO;
import ml.pfit.dto.TraceRequestDTO;
import ml.pfit.mapper.CountryRequestMapper;
import ml.pfit.mapper.CurrencyRequestMapper;
import ml.pfit.mapper.IPRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


@Component
public class RequestResolver {

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
        // Retrieve info from the APIs
        ipMapper.map(ipResolver.resolve(traceRequestDTO.getIp()), traceRequestDTO);
        countryMapper.map(countryResolver.resolve(traceRequestDTO.getCode()), traceRequestDTO);
        currencyMapper.map(currencyResolver.resolve(traceRequestDTO.getCurrency()), traceRequestDTO);
        traceRequestDTO.calculateLocalTimes();
        return traceRequestDTO;
    }

}
