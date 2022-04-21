package ml.pfit.resolve;

import ml.pfit.model.Currency;

public class CurrencyMockResolver implements CurrencyResolverInterface {

    @Override
    public Currency resolve(String currencyCode) {
        return new Currency("ARS", 115.0);
    }
}
