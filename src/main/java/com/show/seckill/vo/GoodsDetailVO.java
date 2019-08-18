package com.show.seckill.vo;

import com.show.seckill.domain.MiaoshaUser;

/**
 * @Auther: 涂成
 * @Date: 2019/6/26 16:54
 * @Description:
 */
public class GoodsDetailVO {

    private int miaoshaStatus;
    private long remainSeconds;
    MiaoshaUser user;
    GoodsVO goods;

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public long getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(long remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public GoodsVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsVO goods) {
        this.goods = goods;
    }
}
