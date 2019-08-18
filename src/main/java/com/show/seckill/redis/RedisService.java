package com.show.seckill.redis;

import com.alibaba.fastjson.JSON;
import com.show.seckill.redis.keys.KeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Auther: 涂成
 * @Date: 2019/6/18 20:08
 * @Description:
 */
@Service
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    JedisPool jedisPool;

    /**
     * 通过key从redis中获取对象
     *
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String s = jedis.get(realKey);
            if (s == null || s.length() <= 0) {
                return null;
            }
            T t = stringToBean(s, clazz);
            return t;
        } catch (Exception e) {
            logger.error("redisService:get{}", e.getMessage());
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 将key和对象存入到redis中
     *
     * @param keyPrefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix keyPrefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String s = beanToValue(value);
            if (s == null || s.length() <= 0) {
                return false;
            }
            String realkey = keyPrefix.getPrefix() + key;
            int seconds = keyPrefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realkey, s);
            } else {
                jedis.setex(realkey, seconds, s);
            }
            return true;
        } catch (Exception e) {
            logger.error("redisService:set{}", e.getMessage());
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断redis中是否含有某个key
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.del(realKey) > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }


    /**
     * 减少值,减少后会返回新的值
     *
     * @param prefix
     * @param key
     * @return
     */
    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }


    /**
     * 将bean对象转换为String
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String beanToValue(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (value.getClass() == int.class || value.getClass() == Integer.class) {
            return "" + value;
        } else if (value.getClass() == long.class || value.getClass() == Long.class) {
            return "" + value;
        } else if (value.getClass() == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 将String对象转换为bean对象
     *
     * @param s
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String s, Class<T> clazz) {
        if (s == null || s.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(s);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(s);
        } else if (clazz == String.class) {
            return (T) s;
        } else {
            return JSON.toJavaObject(JSON.parseObject(s), clazz);
        }

    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
