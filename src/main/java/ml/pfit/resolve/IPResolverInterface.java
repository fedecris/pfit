package ml.pfit.resolve;

import ml.pfit.dto.TraceRequest;

public interface IPResolverInterface {

    public void resolve(TraceRequest traceRequest) throws Exception;
}
