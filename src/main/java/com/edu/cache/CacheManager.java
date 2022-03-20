package com.edu.cache;


import java.util.concurrent.TimeUnit;


public interface CacheManager<T>  {
    void put(String key, T value,  Long expired,TimeUnit timeUnit);

    T get(String key);

    void delete(String key);

    Boolean containsKey(String key);
}