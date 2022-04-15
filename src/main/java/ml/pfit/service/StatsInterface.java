package ml.pfit.service;

import ml.pfit.dto.TraceRequest;
import org.json.simple.JSONObject;

public interface StatsInterface {

    /** Stores a new request
     * @param traceRequest the information related to this request */
    void storeRequest(TraceRequest traceRequest);

    /** @return the farthest distance in KM from Buenos Aires considering all API calls */
    Integer maxDistance();

    /** @return the nearest distance in KM from Buenos Aires considering all API calls  */
    Integer minDistance();

    /** @return the average distance in KM from Buenos Aires considering all API calls  */
    Integer avgDistance();

    /** @return a JSON representation of this info */
    default JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("min", minDistance());
        obj.put("max", maxDistance());
        obj.put("avg", avgDistance());
        return obj;
    }
}
