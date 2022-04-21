package ml.pfit.resolve;

import ml.pfit.model.IPAddr;

public interface IPResolverInterface {

    IPAddr resolve(String ip) throws Exception;
}
