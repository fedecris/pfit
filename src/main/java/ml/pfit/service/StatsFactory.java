package ml.pfit.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StatsFactory {

    /** Single instance */
    StatsInterface instance = null;

    @Value("${stats.service.class}")
    private String STATS_IMPL_CLASS;

    /** Retrieves an implementation instance of StatsInterface according to the configuration
     * @throws Exception if class is not found at runtime */
    public StatsInterface getInstance() throws Exception {
        if (instance == null) {
            Class clazz = Class.forName(STATS_IMPL_CLASS);
            instance = (StatsInterface)clazz.newInstance();
        }
        return instance;
    }

}
