package com.company.promotionapi.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfiguration {

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {

        Map<String, Long> cacheMap = new HashMap<>();
        cacheMap.put("campaign-by-team", 60L);

        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setExpires(cacheMap);

        return cacheManager;
    }

}
