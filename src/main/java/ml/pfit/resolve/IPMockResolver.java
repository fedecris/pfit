package ml.pfit.resolve;

import ml.pfit.dto.TraceRequest;
import org.json.simple.JSONObject;

public class IPMockResolver implements IPResolverInterface {

    public void resolve(TraceRequest traceRequest) throws Exception {
        traceRequest.setCode("ARG");
        traceRequest.setName("Argentina");
    }
}
