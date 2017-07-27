package com.example.repository;

import java.util.Map;

/**
 * Created by aliatakan on 16/07/17.
 */
public interface RedisRepository {
    //void addToCard(Product cardProduct);

    //List<Product> getCardById(String cardId);

    String hget(String key, String field);

    Map<String,String> hgetall(String key);

    void hset(String key, String field, String value);

    void hincrby(String key, String field, long increment);

    void hmset (String key, Map<String,String> pairs);

    boolean exists(String key);

}
