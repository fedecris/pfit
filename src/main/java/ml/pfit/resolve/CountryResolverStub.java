package ml.pfit.resolve;

import ml.pfit.model.Country;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryResolverStub implements CountryResolverInterface {

    @Override
    public Country resolve(String countryCode) {
        return new Country("ARS", new ArrayList<>(Arrays.asList("Spanish")), new ArrayList<>(Arrays.asList("UTC-3")), 500, "ARS");
    }
}
