package com.show.seckill.service;

import com.show.seckill.vo.GoodsVO;

import java.util.List;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 23:55
 * @Description:
 */

public interface GoodsService {

    /**
     * 获取所有的秒杀商品
     *
     * @return
     */
    List<GoodsVO> listGoods();

    /**
     * 通过id查询到具体的秒杀商品信息
     *
     * @param goodsId
     * @return
     */
    GoodsVO findGoodsVoById(Long goodsId);

    /**
     * 执行秒杀，减库存
     *
     * @param goods
     */
    int reduceStock(GoodsVO goods);
}
