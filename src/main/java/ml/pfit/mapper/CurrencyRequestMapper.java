package ml.pfit.mapper;

import ml.pfit.model.Currency;
import ml.pfit.dto.TraceRequestDTO;
import org.mapstruct.*;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrencyRequestMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "currencyRateUSD", source = "currencyRateUSD")
    void map(Currency dto, @MappingTarget TraceRequestDTO traceDTO);

}
