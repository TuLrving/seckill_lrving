package com.show.seckill.dao;

import com.show.seckill.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 23:49
 * @Description: 秒杀商品
 */
@Mapper
public interface GoodsDao {

    /**
     * 获取所有的秒杀商品
     *
     * @return
     */
    @Select("SELECT g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price " +
            "FROM miaosha_goods mg LEFT JOIN GOODS g " +
            "ON mg.goods_id = g.id")
    List<GoodsVO> listGoods();

    /**
     * 通过商品ID获取某个具体的商品信息
     *
     * @param goodsId
     * @return
     */
    @Select("SELECT g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price " +
            "FROM miaosha_goods mg LEFT JOIN GOODS g " +
            "ON mg.goods_id = g.id where mg.goods_id = #{goodsId}")
    GoodsVO findGoodVoById(@Param("goodsId") Long goodsId);

    /**
     * 执行秒杀，减库存
     *
     * @param goods
     */
    @Update("UPDATE miaosha_goods SET stock_count = stock_count - 1 WHERE goods_id = #{id} AND stock_count > 0")
    int reduceStock(GoodsVO goods);
}
