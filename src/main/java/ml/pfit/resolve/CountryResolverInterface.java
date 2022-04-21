package ml.pfit.resolve;

import ml.pfit.model.Country;

public interface CountryResolverInterface {

    Country resolve(String countryCode) throws Exception;
}
