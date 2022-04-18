package ml.pfit.resolve;

import ml.pfit.dto.TraceRequest;

public interface CountryResolverInterface {

    public void resolve(TraceRequest traceRequest) throws Exception;
}
