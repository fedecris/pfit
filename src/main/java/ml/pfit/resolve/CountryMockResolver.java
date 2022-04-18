package ml.pfit.resolve;

import ml.pfit.dto.TraceRequest;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryMockResolver implements CountryResolverInterface {

    public void resolve(TraceRequest traceRequest) throws Exception {
        traceRequest.setCurrency("ARS");
        traceRequest.setLanguages(new ArrayList<>(Arrays.asList("Spanish")));
        traceRequest.setTimeZones(new ArrayList<>(Arrays.asList("UTC-3")));
        traceRequest.setDistance(500);
        traceRequest.calculateLocalTimes();
    }
}
