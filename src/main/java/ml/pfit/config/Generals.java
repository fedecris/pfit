package ml.pfit.config;

import ml.pfit.cache.CacheManager;
import ml.pfit.dto.TraceRequest;
import ml.pfit.resolve.CountryResolver;
import ml.pfit.resolve.CurrencyResolver;
import ml.pfit.resolve.IPResolver;
import ml.pfit.service.StatsInterface;
import ml.pfit.service.VolatileStatsImpl;
import ml.pfit.utils.RemoteAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
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
    public IPResolver getIPResolver() { return new IPResolver(remoteAPI, cacheManager); }

    @Bean
    @Primary
    public CountryResolver getCountryResolver() { return new CountryResolver(remoteAPI, cacheManager); }

    @Bean
    @Primary
    public CurrencyResolver getCurrencyResolver() { return new CurrencyResolver(remoteAPI, cacheManager); }
}
