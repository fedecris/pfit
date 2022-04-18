package ml.pfit.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DefaultCacheManager {

    @Bean("default.cache")
    @Primary
    public CacheManager defaultCacheManager() {
        return new ConcurrentMapCacheManager();
    }

}
