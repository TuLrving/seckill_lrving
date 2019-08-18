package com.show.seckill.redis.keys;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 08:07
 * @Description:
 */
public class UserKey extends BasePrefix {
    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
