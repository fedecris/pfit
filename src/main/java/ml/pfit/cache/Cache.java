package ml.pfit.cache;

import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    /** Basic cache */
    protected ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    /** Time to live before reset, expressed in milliseconds */
    protected Integer expireMillis;

    /** Last cache reset */
    protected Long lastReset;

    /** Adds an item to the cache */
    public void put(String cacheKey, Object value) {
        cache.put(cacheKey, value);
    }

    /** Retrieves an item from the cache */
    public synchronized Object get(String cacheKey) {
        if (expired()) return null;
        return cache.get(cacheKey);
    }

    /** Empties the cache */
    public void reset() {
        cache.clear();
        lastReset = System.currentTimeMillis();
    }

    /** Checks if the cache has expired */
    protected boolean expired() {
        if (expireMillis != null && lastReset + expireMillis < System.currentTimeMillis()) {
            reset();
            return true;
        }
        return false;
    }

    /** Default constructor
     *  @param expiresMin time to live */
    public Cache(Integer expiresMin) {
        if (expiresMin!=null) {
            this.expireMillis = expiresMin * 60 * 1000;
            this.lastReset = System.currentTimeMillis();
        }
    }
}
