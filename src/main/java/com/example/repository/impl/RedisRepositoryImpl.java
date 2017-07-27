package com.example.repository.impl;

import com.example.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by aliatakan on 16/07/17.
 */
@Repository
public class RedisRepositoryImpl implements RedisRepository {

    @Autowired
    private StringRedisTemplate template;

    @Override
    public String hget(String key, String field) {

        return template.opsForHash().get(key,field).toString();
    }

    @Override
    public Map<String, String> hgetall(String key) {
        return template.<String,String>opsForHash().entries(key);
    }

    @Override
    public void hset(String key, String field, String value) {
        template.opsForHash().put(key,field,value);
    }

    @Override
    public void hincrby(String key, String field, long increment) {
        template.opsForHash().increment(key,field,increment);
    }

    @Override
    public void hmset(final String key, final Map<String,String> pairs) {
        template.opsForHash().putAll(key,pairs);
    }

    @Override
    public boolean exists(String key) {
        return template.hasKey(key);
    }

}
