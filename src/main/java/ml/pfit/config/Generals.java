package ml.pfit.config;

import ml.pfit.service.StatsInterface;
import ml.pfit.service.VolatileStatsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Generals {

    @Bean
    @Primary
    public StatsInterface getStats() {
        return new VolatileStatsImpl();
    }
}
