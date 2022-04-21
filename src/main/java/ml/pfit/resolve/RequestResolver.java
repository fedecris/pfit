package ml.pfit.resolve;

import lombok.RequiredArgsConstructor;
import ml.pfit.model.IPAddr;
import ml.pfit.dto.TraceRequestDTO;
import ml.pfit.mapper.CountryRequestMapper;
import ml.pfit.mapper.CurrencyRequestMapper;
import ml.pfit.mapper.IPRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RequestResolver {

    private final IPResolverInterface ipResolver;

    private final CountryResolverInterface countryResolver;

    private final CurrencyResolverInterface currencyResolver;

    private final IPRequestMapper ipMapper;

    private final CountryRequestMapper countryMapper;

    private final CurrencyRequestMapper currencyMapper;

    /** Retrieves the complete information of a request
     *  @param traceRequestDTO the request to solve
     *  @return the retrieved information related with the Country */
    public TraceRequestDTO resolve(TraceRequestDTO traceRequestDTO) throws Exception {
        // Retrieve info from the APIs
        IPAddr ipr = ipResolver.resolve(traceRequestDTO.getIp());
        ipMapper.map(ipr, traceRequestDTO);
        countryMapper.map(countryResolver.resolve(traceRequestDTO.getCode()), traceRequestDTO);
        currencyMapper.map(currencyResolver.resolve(traceRequestDTO.getCurrency()), traceRequestDTO);
        return traceRequestDTO;
    }

}
