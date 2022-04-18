package ml.pfit.config;

import ml.pfit.dto.TraceRequest;
import ml.pfit.resolve.*;
import ml.pfit.service.StatsInterface;
import ml.pfit.service.VolatileStatsImpl;
import ml.pfit.utils.RemoteAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@Profile("prod")
@Configuration
@EnableAspectJAutoProxy
@EnableCaching
public class GeneralProdConfiguration {

    @Autowired
    private RemoteAPI remoteAPI;

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
    public IPResolverInterface getIPResolver() { return new IPResolver(remoteAPI); }

    @Bean
    @Primary
    public CountryResolverInterface getCountryResolver() { return new CountryResolver(remoteAPI); }

    @Bean
    @Primary
    public CurrencyResolverInterface getCurrencyResolver() { return new CurrencyResolver(remoteAPI); }

}
