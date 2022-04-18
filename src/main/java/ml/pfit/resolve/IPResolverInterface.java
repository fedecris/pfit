package ml.pfit.resolve;

import ml.pfit.dto.IPRequestDTO;
import ml.pfit.dto.TraceRequestDTO;

public interface IPResolverInterface {

    IPRequestDTO resolve(String ip) throws Exception;
}
