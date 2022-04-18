package ml.pfit.resolve;

import ml.pfit.dto.CurrencyRequestDTO;
import ml.pfit.dto.TraceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class CurrencyMockResolver implements CurrencyResolverInterface {

    @Autowired
    CurrencyRequestDTO dto;

    @Override
    public CurrencyRequestDTO resolve(String currencyCode) {
        dto.setCurrencyRateUSD(115.0);
        return dto;
    }
}
