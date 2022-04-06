package ml.pfit.service;

import ml.pfit.model.Country;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * A simple volatile in-memory statistics store implementation
 */
@Component
public class VolatileStatsImpl implements StatsInterface {

    /** Distance accumulator */
    protected static Long totalDistance = 0L;

    /** Requests accumulator */
    protected static Long totalRequest = 0L;

    /** Farthest distance */
    protected static Integer maxDist = Integer.MIN_VALUE;

    /** Nearest distance */
    protected static Integer minDist = Integer.MAX_VALUE;

    @Override
    public synchronized void storeRequest(Country country) {
        // Accumulative distance & requests count
        totalDistance += country.getDistance();
        totalRequest += 1;
        // Set max, min distances (if corresponds)
        maxDist = Math.max(maxDist, country.getDistance());
        minDist = Math.min(minDist, country.getDistance());
    }

    @Override
    public Integer maxDistance() {
        return maxDist != Integer.MIN_VALUE ? maxDist : 0;
    }

    @Override
    public Integer minDistance() {
        return minDist != Integer.MAX_VALUE ? minDist : 0;
    }

    @Override
    public Integer avgDistance() {
        return totalRequest > 0 ? Math.toIntExact(totalDistance / totalRequest) : 0;
    }
}
