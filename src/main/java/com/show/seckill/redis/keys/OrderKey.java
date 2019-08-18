package com.show.seckill.redis.keys;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 08:09
 * @Description:
 */
public class OrderKey extends BasePrefix {

    //订单有效期默认十年
    private static final int EXPIRE_SECONDS = 60 * 60 * 24 * 365 * 10;

    public OrderKey(String prefix) {
        super(prefix);
    }

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderKey getByOIdGId = new OrderKey(EXPIRE_SECONDS, "oid:gid");
}
