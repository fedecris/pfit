package ml.pfit.mapper;

import ml.pfit.model.Country;
import ml.pfit.dto.TraceRequestDTO;
import org.mapstruct.*;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryRequestMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "languages", source = "languages")
    @Mapping(target = "timeZones", source = "timeZones")
    @Mapping(target = "distance", source = "distance")
    @Mapping(target = "currency", source = "currency")
    void map(Country dto, @MappingTarget TraceRequestDTO traceDTO);

}
