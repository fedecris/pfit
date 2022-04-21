package ml.pfit.resolve;

import ml.pfit.model.IPAddr;

public class IPMockResolver implements IPResolverInterface {

    @Override
    public IPAddr resolve(String ip) throws Exception {
        return new IPAddr(ip, "ARG", "Argentina");
    }
}
