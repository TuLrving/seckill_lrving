package com.show.seckill.redis.keys;

/**
 * @Auther: 涂成
 * @Date: 2019/6/30 22:15
 * @Description:
 */
public class MiaoshaKey extends BasePrefix {
    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    //页面缓存有效期默认为一分钟
    public static MiaoshaKey getGoodsOver = new MiaoshaKey(60 * 60 * 24 * 365 * 100, "goodsOver");
}
