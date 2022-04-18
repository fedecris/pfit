package ml.pfit.resolve;

import ml.pfit.dto.IPRequestDTO;
import ml.pfit.dto.TraceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class IPMockResolver implements IPResolverInterface {

    @Autowired
    IPRequestDTO dto;

    @Override
    public IPRequestDTO resolve(String ip) throws Exception {
        dto.setCode("ARG");
        dto.setName("Argentina");
        return dto;
    }
}
