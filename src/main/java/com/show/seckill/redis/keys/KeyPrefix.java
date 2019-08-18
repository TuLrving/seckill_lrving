package com.show.seckill.redis.keys;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 08:02
 * @Description:
 */
public interface KeyPrefix {

    int expireSeconds();//有效时间

    String getPrefix();
}
