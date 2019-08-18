package com.show.seckill.service.impl;

import com.show.seckill.dao.GoodsDao;
import com.show.seckill.service.GoodsService;
import com.show.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 23:56
 * @Description:
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsDao goodsDao;

    /**
     * 获取所有的秒杀商品
     *
     * @return
     */
    @Override
    public List<GoodsVO> listGoods() {
        return goodsDao.listGoods();
    }

    /**
     * 通过id查询到具体的秒杀商品信息
     *
     * @param goodsId
     * @return
     */
    @Override
    public GoodsVO findGoodsVoById(Long goodsId) {
        return goodsDao.findGoodVoById(goodsId);
    }

    /**
     * 执行秒杀，减库存
     *
     * @param goods
     */
    @Override
    public int reduceStock(GoodsVO goods) {
        return goodsDao.reduceStock(goods);
    }
}
