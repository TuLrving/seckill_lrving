package com.show.seckill.rabbitmq;

import com.show.seckill.domain.MiaoshaUser;

/**
 * @Auther: 涂成
 * @Date: 2019/6/30 20:47
 * @Description:
 */
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;

    public MiaoshaMessage() {
    }

    public MiaoshaMessage(MiaoshaUser user, long goodsId) {
        this.user = user;
        this.goodsId = goodsId;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
