package ml.pfit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;

@Component
public class StatsFactory {

    /** Single instance */
    StatsInterface instance = null;

    /** Available implementations of StatsInterface */
    private static HashMap<String, StatsInterface> instances = new HashMap<>();

    @Value("${stats.service}")
    private String STATS_IMPL;

    @Autowired
    VolatileStatsImpl memoryImpl;

    @Autowired
    MongoStatsImpl mongoImpl;


    /** Retrieves an implementation instance of StatsInterface according to the configuration
     * @throws Exception if class is not found at runtime */
    public StatsInterface getInstance() throws Exception {
        if (instance == null) {
            init();
            instance = instances.get(STATS_IMPL.trim().toLowerCase());
            if (instance == null) {
                throw new Exception("Unknown stats implementation");
            }
        }
        return instance;
    }

    protected void init() {
        instances.put("memory", memoryImpl);
        instances.put("mongo", mongoImpl);
    }
}
