package ml.pfit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyRequestDTO {

    private String currency;

    private Double currencyRateUSD;

}
