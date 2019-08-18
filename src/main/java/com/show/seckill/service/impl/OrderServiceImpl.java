package com.show.seckill.service.impl;

import com.show.seckill.dao.OrderDao;
import com.show.seckill.domain.MiaoshaOrder;
import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.domain.OrderInfo;
import com.show.seckill.redis.RedisService;
import com.show.seckill.redis.keys.OrderKey;
import com.show.seckill.service.OrderService;
import com.show.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Auther: 涂成
 * @Date: 2019/6/23 21:14
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    /**
     * 通过MiaoshaUser的ID和商品ID查询是否已经有秒杀成功的订单了
     *
     * @param id
     * @param goodsId
     * @return
     */
    @Override
    public MiaoshaOrder findOrderByUserIdGoodsId(Long id, Long goodsId) {
        return orderDao.findOrderByUserIdGoodsId(id, goodsId);
    }


    /**
     * 秒杀后创建订单
     *
     * @param user
     * @param goods
     * @return
     */
    @Override
    @Transactional

    public OrderInfo createOrder(MiaoshaUser user, GoodsVO goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setUserId(user.getId());
        orderInfo.setStatus(0);
        orderInfo.setOrderChannel(0);
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsCount(1);
        orderInfo.setDeliveryAddrId(1L);
        //创建订单详情并存入数据库中
        orderDao.createOrder(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        //创建秒杀订单并存入数据库中
        orderDao.createMiaoshaOrder(miaoshaOrder);
        //将订单信息存入缓存中
        redisService.set(OrderKey.getByOIdGId, user.getId() + ":" + goods.getId(), miaoshaOrder);
        return orderInfo;
    }

    /**
     * 通过orderId查询相应的订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    /**
     * 在缓存中查询订单信息
     *
     * @param id
     * @param goodsId
     * @return
     */
    @Override
    public MiaoshaOrder getMiaoshaOrderByCache(Long id, Long goodsId) {
        return redisService.get(OrderKey.getByOIdGId, id + ":" + goodsId, MiaoshaOrder.class);
    }
}
