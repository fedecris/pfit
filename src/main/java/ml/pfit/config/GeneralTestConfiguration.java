package ml.pfit.config;

import ml.pfit.cache.CacheManager;
import ml.pfit.dto.TraceRequest;
import ml.pfit.resolve.*;
import ml.pfit.service.StatsInterface;
import ml.pfit.service.VolatileStatsImpl;
import ml.pfit.utils.RemoteAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@Profile("test")
@Configuration
@EnableAspectJAutoProxy
public class GeneralTestConfiguration {

    @Autowired
    private RemoteAPI remoteAPI;

    @Autowired
    private CacheManager cacheManager;

    @Bean
    @Primary
    public StatsInterface getStats() {
        return new VolatileStatsImpl();
    }

    @Bean
    @RequestScope
    public TraceRequest request() {
        return new TraceRequest();
    }

    @Bean
    @Primary
    public IPResolverInterface getIPResolverTest() { return new IPMockResolver(); }

    @Bean
    @Primary
    public CountryResolverInterface getCountryResolverTest() { return new CountryMockResolver(); }

    @Bean
    @Primary
    public CurrencyResolverInterface getCurrencyResolverTest() { return new CurrencyMockResolver(); }

}
