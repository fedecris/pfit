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

@Configuration
@EnableAspectJAutoProxy
public class Generals {

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
    @Profile("prod")
    public IPResolverInterface getIPResolver() { return new IPResolver(remoteAPI, cacheManager); }

    @Bean
    @Primary
    @Profile("prod")
    public CountryResolverInterface getCountryResolver() { return new CountryResolver(remoteAPI, cacheManager); }

    @Bean
    @Primary
    @Profile("prod")
    public CurrencyResolverInterface getCurrencyResolver() { return new CurrencyResolver(remoteAPI, cacheManager); }

    @Bean
    @Primary
    @Profile("test")
    public IPResolverInterface getIPResolverTest() { return new IPMockResolver(); }

    @Bean
    @Primary
    @Profile("test")
    public CountryResolverInterface getCountryResolverTest() { return new CountryMockResolver(); }

    @Bean
    @Primary
    @Profile("test")
    public CurrencyResolverInterface getCurrencyResolverTest() { return new CurrencyMockResolver(); }
}
