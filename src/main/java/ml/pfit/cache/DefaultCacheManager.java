package ml.pfit.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DefaultCacheManager {

    @Bean("default.cache")
    public CacheManager defaultCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("ips"),
                new ConcurrentMapCache("countries"),
                new ConcurrentMapCache("currencies")
                ));
        return cacheManager;
    }

}
