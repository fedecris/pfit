package ml.pfit.resolve;

import ml.pfit.dto.CountryRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryMockResolver implements CountryResolverInterface {

    @Autowired
    CountryRequestDTO dto;

    @Override
    public CountryRequestDTO resolve(String countryCode) {
        dto.setCurrency("ARS");
        dto.setLanguages(new ArrayList<>(Arrays.asList("Spanish")));
        dto.setTimeZones(new ArrayList<>(Arrays.asList("UTC-3")));
        dto.setDistance(500);
        return dto;
    }
}
