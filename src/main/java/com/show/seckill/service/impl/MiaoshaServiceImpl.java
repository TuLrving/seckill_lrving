package com.show.seckill.service.impl;

import com.show.seckill.domain.MiaoshaOrder;
import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.domain.OrderInfo;
import com.show.seckill.redis.RedisService;
import com.show.seckill.redis.keys.MiaoshaKey;
import com.show.seckill.service.GoodsService;
import com.show.seckill.service.MiaoshaService;
import com.show.seckill.service.OrderService;
import com.show.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: 涂成
 * @Date: 2019/6/23 21:26
 * @Description:
 */
@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    /**
     * 执行秒杀
     *
     * @param user
     * @param goods
     * @return
     */
    @Override
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVO goods) {
        //1.减库存
        int i = goodsService.reduceStock(goods);
        if (i <= 0) {//减库存失败，回滚
            setGoodsOver(goods.getId());
            return null;
        } else {
            //2.创建订单
            return orderService.createOrder(user, goods);
        }

    }

    /**
     * 查询订单信息
     *
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    public long getMiaoshaOrderResult(Long userId, Long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByCache(userId, goodsId);
        if (order != null) {
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                //商品没有库存
                return -1;
            } else {
                //任务还未完成
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.getGoodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(Long goodsId) {
        return redisService.exists(MiaoshaKey.getGoodsOver, "" + goodsId);
    }
}
