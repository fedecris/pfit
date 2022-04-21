package ml.pfit.resolve;

import ml.pfit.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;

public class CurrencyMockResolver implements CurrencyResolverInterface {

    @Autowired
    Currency dto;

    @Override
    public Currency resolve(String currencyCode) {
        dto.setCurrencyRateUSD(115.0);
        return dto;
    }
}
