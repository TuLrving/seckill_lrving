package com.show.seckill.redis.keys;

/**
 * @Auther: 涂成
 * @Date: 2019/6/25 22:03
 * @Description:
 */
public class GoodsKey extends BasePrefix {

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    //页面缓存有效期默认为一分钟
    public static GoodsKey getGoodsList = new GoodsKey(60, "goodsList");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "goodsDetail");
    public static GoodsKey getGoodsStockCount = new GoodsKey(60 * 60 * 24 * 365 * 100, "goodsCount");
}
