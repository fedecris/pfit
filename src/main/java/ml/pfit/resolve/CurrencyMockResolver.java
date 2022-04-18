package ml.pfit.resolve;

import ml.pfit.dto.TraceRequest;
import org.json.simple.JSONObject;

public class CurrencyMockResolver implements CurrencyResolverInterface {

    public void resolve(TraceRequest traceRequest)  {
         traceRequest.setCurrencyRateUSD(115.0);
    }

}
