package ml.pfit.resolve;

import ml.pfit.dto.CurrencyRequestDTO;


public interface CurrencyResolverInterface {

    CurrencyRequestDTO resolve(String currencyCode);
}
