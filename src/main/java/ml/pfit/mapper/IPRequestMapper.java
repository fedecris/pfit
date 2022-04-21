package ml.pfit.mapper;

import ml.pfit.model.IPAddr;
import ml.pfit.dto.TraceRequestDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public interface IPRequestMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    void map(IPAddr dto, @MappingTarget TraceRequestDTO traceDTO);

}
