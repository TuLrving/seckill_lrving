package com.show.seckill.service;

import com.show.seckill.domain.MiaoshaOrder;
import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.domain.OrderInfo;
import com.show.seckill.vo.GoodsVO;

/**
 * @Auther: 涂成
 * @Date: 2019/6/23 21:12
 * @Description:
 */
public interface OrderService {

    /**
     * 通过MiaoshaUser的ID和商品ID查询是否已经有秒杀成功的订单了
     *
     * @param id
     * @param goodsId
     * @return
     */
    MiaoshaOrder findOrderByUserIdGoodsId(Long id, Long goodsId);

    /**
     * 秒杀后创建订单
     *
     * @param user
     * @param goods
     * @return
     */
    OrderInfo createOrder(MiaoshaUser user, GoodsVO goods);

    /**
     * 通过orderId查询相应的订单信息
     *
     * @param orderId
     * @return
     */
    OrderInfo getOrderById(long orderId);

    /**
     * 在缓存中查询订单信息
     *
     * @param id
     * @param goodsId
     * @return
     */
    MiaoshaOrder getMiaoshaOrderByCache(Long id, Long goodsId);
}
