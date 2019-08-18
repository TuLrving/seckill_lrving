package com.show.seckill.service;

import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.domain.OrderInfo;
import com.show.seckill.vo.GoodsVO;

/**
 * @Auther: 涂成
 * @Date: 2019/6/23 21:24
 * @Description:
 */
public interface MiaoshaService {

    /**
     * 执行秒杀
     *
     * @param user
     * @param goods
     * @return
     */
    OrderInfo miaosha(MiaoshaUser user, GoodsVO goods);

    /**
     * 查询订单信息
     *
     * @param id
     * @param goodsId
     * @return
     */
    long getMiaoshaOrderResult(Long id, Long goodsId);
}
