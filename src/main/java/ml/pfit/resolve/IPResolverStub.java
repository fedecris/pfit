package ml.pfit.resolve;

import ml.pfit.model.IPAddr;
import org.springframework.stereotype.Component;

@Component
public class IPResolverStub implements IPResolverInterface {

    @Override
    public IPAddr resolve(String ip) throws Exception {
        return new IPAddr(ip, "ARG", "Argentina");
    }
}
