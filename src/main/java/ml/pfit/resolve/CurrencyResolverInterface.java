package ml.pfit.resolve;

import ml.pfit.model.Currency;


public interface CurrencyResolverInterface {

    Currency resolve(String currencyCode);
}
