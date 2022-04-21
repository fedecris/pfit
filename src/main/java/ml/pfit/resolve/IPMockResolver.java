package ml.pfit.resolve;

import ml.pfit.model.IPAddr;
import org.springframework.beans.factory.annotation.Autowired;

public class IPMockResolver implements IPResolverInterface {

    @Autowired
    IPAddr dto;

    @Override
    public IPAddr resolve(String ip) throws Exception {
        dto.setCode("ARG");
        dto.setName("Argentina");
        return dto;
    }
}
