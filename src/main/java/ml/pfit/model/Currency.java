package ml.pfit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Currency {

    private final String currency;

    private Double currencyRateUSD;

    public Currency(String currency, Double currencyRateUSD) {
        this.currency = currency;
        this.currencyRateUSD = currencyRateUSD;
    }
}
