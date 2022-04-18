package ml.pfit.resolve;

import ml.pfit.dto.CountryRequestDTO;

public interface CountryResolverInterface {

    CountryRequestDTO resolve(String countryCode) throws Exception;
}
