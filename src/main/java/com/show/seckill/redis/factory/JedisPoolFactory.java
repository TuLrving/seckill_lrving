package com.show.seckill.redis.factory;

import com.show.seckill.redis.configuration.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: 涂成
 * @Date: 2019/6/18 20:28
 * @Description:
 */
@Component
public class JedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool getJedisPool() {
        System.out.println(redisConfig);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait());
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(),
                redisConfig.getTimeout());
        return jedisPool;
    }

}
