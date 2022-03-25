package ml.pfit.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CacheManager {

    /** Cache instances. Name of the cache -> Cache */
    HashMap<String, Cache> instances = new HashMap<>();

    /** Retrieves a specific cache. Creates a new one if the cache does not exist
     * @param name of the cache to retrieve */
    public Cache getInstance(String name) {
        return getInstance(name, null);
    }

    /** Retrieves a specific cache.  Creates a new one if the cache does not exist.
     * @param name of the cache to retrieve
     * @param expiresMins time to live for the items of the cache */
    public Cache getInstance(String name, Integer expiresMins) {
        if (instances.get(name)==null) {
            instances.put(name, new Cache(expiresMins));
        }
        return instances.get(name);
    }
}
