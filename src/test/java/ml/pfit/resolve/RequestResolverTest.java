package ml.pfit.resolve;

import ml.pfit.dto.TraceRequestDTO;
import ml.pfit.mapper.CountryRequestMapper;
import ml.pfit.mapper.CurrencyRequestMapper;
import ml.pfit.mapper.IPRequestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RequestResolverTest {

    @Autowired
    private IPResolverInterface ipResolver;

    @Autowired
    private CountryResolverInterface countryResolver;

    @Autowired
    private CurrencyResolverInterface currencyResolver;

    @Autowired
    private IPRequestMapper ipMapper;

    @Autowired
    private CountryRequestMapper countryMapper;

    @Autowired
    private CurrencyRequestMapper currencyMapper;

    @Test
    void requestShouldReturnIPCountryAndCurrencyInfo() throws Exception {
        RequestResolver req = new RequestResolver(ipResolver, countryResolver, currencyResolver, ipMapper, countryMapper, currencyMapper);
        TraceRequestDTO resp = req.resolve(new TraceRequestDTO());
        assertThat(resp.getCode()).isEqualTo("ARG");
        assertThat(resp.getDistance()).isEqualTo(500);
        assertThat(resp.getCurrencyRateUSD()).isEqualTo(115.0);
    }
}
