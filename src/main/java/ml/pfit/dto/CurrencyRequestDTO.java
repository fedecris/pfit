package ml.pfit.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Getter
@Setter
public class CurrencyRequestDTO {

    private String currency;

    private Double currencyRateUSD;

}
