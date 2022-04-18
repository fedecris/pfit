package ml.pfit.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Getter
@Setter
public class CountryRequestDTO {

    private String code;

    private ArrayList<String> languages;

    private ArrayList<String> timeZones;

    private Integer distance;

    private String currency;

}
