package ml.pfit.resolve;

import ml.pfit.model.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyResolverStub implements CurrencyResolverInterface {

    @Override
    public Currency resolve(String currencyCode) {
        return new Currency("ARS", 115.0);
    }
}
