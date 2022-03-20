package com.edu.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisCacheManageImpl <T> implements CacheManager<T>{

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void put(String key, T value,  Long expired,TimeUnit timeUnit) {
        redisUtil.addKey(key, value, expired, timeUnit);
    }

    @Override
    public T get(String key) {
        return(T) redisUtil.get(key);
    }


    @Override
    public void delete(String key) {
        redisUtil.del(key);
    }

    @Override
    public Boolean containsKey(String key) {
        return redisUtil.hasKey(key);
    }
}
