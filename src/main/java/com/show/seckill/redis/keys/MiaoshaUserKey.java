package com.show.seckill.redis.keys;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 15:57
 * @Description:
 */
public class MiaoshaUserKey extends BasePrefix {

    private static final int TOKEN_EXPIRE = 3600 * 24 * 2;//默认有效时间为2天
    private static final int FINAL_EXPIRE = 3600 * 24 * 7;//默认七天有效

    public MiaoshaUserKey(String prefix) {
        super(prefix);
    }

    public MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "token");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(FINAL_EXPIRE, "userId");
}
