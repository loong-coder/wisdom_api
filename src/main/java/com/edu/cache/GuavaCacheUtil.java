package com.edu.cache;

import com.google.common.cache.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCacheUtil {

    /**
     * max size
     */
    private static final long GUAVA_CACHE_SIZE = 10000;

    /**
     * cache hours
     */
//    private static final long GUAVA_CACHE_HOURS = 1;

    private static final long GUAVA_CACHE_SECONDS = 1 * 60 * 60;

    /**
     * cache element
     */
    private static LoadingCache<String, String> GLOBAL_CACHE = null;

    static {
        try {
            GLOBAL_CACHE = loadCache(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    log.info("load:{}", key);
                    return "";
                }
            });
        } catch (Exception e) {
            log.error("Init Guava Cache error", e);
        }
    }


    /**
     * global cache
     * <p>
     * max size：10000
     * cache hours：1
     *
     * @param cacheLoader
     * @return
     * @throws Exception
     */
    private static LoadingCache<String, String> loadCache(CacheLoader<String, String> cacheLoader) throws Exception {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //cache size
                .maximumSize(GUAVA_CACHE_SIZE)
                //cache time
                .expireAfterAccess(GUAVA_CACHE_SECONDS, TimeUnit.SECONDS)
                // expries time
                .expireAfterWrite(GUAVA_CACHE_SECONDS, TimeUnit.SECONDS)
                //remove listener
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> rn) {
                        log.info("onRemoval");
                        //逻辑操作
                        GLOBAL_CACHE.invalidate(rn);
                    }
                })
                //add Guava Cache static
                .recordStats()
                .build(cacheLoader);
        return cache;
    }

    /**
     * set cache
     *
     * @param key
     * @param value
     */
    public static void put(String key, String value) {
        try {
            log.info("put:{}", value);
            GLOBAL_CACHE.put(key, value);
        } catch (Exception e) {
            log.error("set cache error", e);
        }
    }

    /**
     * set cache
     *
     * @param map
     */
    public static void putAll(Map<? extends String, ? extends String> map) {
        try {
            GLOBAL_CACHE.putAll(map);
        } catch (Exception e) {
            log.error("set cache error", e);
        }
    }

    /**
     * get cache
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        String token = "";
        try {
            token = GLOBAL_CACHE.get(key);
        } catch (Exception e) {
            log.error("get cache error", e);
        }
        return token;
    }

    /**
     * remove cache
     *
     * @param key
     */
    public static void remove(String key) {
        try {
            GLOBAL_CACHE.invalidate(key);
        } catch (Exception e) {
            log.error("remove cache error", e);
        }
    }

    /**
     * remove all cache
     *
     * @param keys
     */
    public static void removeAll(Iterable<String> keys) {
        try {
            GLOBAL_CACHE.invalidateAll(keys);
        } catch (Exception e) {
            log.error("remove cache error", e);
        }
    }

    /**
     * remove all  cache
     */
    public static void removeAll() {
        try {
            GLOBAL_CACHE.invalidateAll();
        } catch (Exception e) {
            log.error("remove all cache error", e);
        }
    }

    /**
     * get cache size
     *
     * @return
     */
    public static long size() {
        long size = 0;
        try {
            size = GLOBAL_CACHE.size();
        } catch (Exception e) {
            log.error("get cache size error", e);
        }
        return size;
    }
}
