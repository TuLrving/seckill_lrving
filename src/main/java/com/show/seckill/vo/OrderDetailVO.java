package com.show.seckill.vo;

import com.show.seckill.domain.OrderInfo;

/**
 * @Auther: 涂成
 * @Date: 2019/6/26 22:32
 * @Description:
 */
public class OrderDetailVO {

    private GoodsVO goods;
    private OrderInfo order;

    public GoodsVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsVO goods) {
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
