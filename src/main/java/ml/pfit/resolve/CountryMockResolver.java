package ml.pfit.resolve;

import ml.pfit.model.Country;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryMockResolver implements CountryResolverInterface {

    @Autowired
    Country dto;

    @Override
    public Country resolve(String countryCode) {
        dto.setCurrency("ARS");
        dto.setLanguages(new ArrayList<>(Arrays.asList("Spanish")));
        dto.setTimeZones(new ArrayList<>(Arrays.asList("UTC-3")));
        dto.setDistance(500);
        return dto;
    }
}
