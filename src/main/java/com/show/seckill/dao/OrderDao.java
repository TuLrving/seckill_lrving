package com.show.seckill.dao;

import com.show.seckill.domain.MiaoshaOrder;
import com.show.seckill.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @Auther: 涂成
 * @Date: 2019/6/23 21:16
 * @Description:
 */
@Mapper
public interface OrderDao {

    @Select("SELECT * FROM miaosha_order WHERE user_id=#{userId} AND goods_id=#{goodsId}")
    MiaoshaOrder findOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    @Insert("INSERT INTO order_info(user_id,goods_id,delivery_addr_id,goods_name,goods_count,goods_price,order_channel,status,create_date)" +
            "VALUES" +
            "(#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyProperty = "id", keyColumn = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long createOrder(OrderInfo orderInfo);

    @Insert("INSERT INTO miaosha_order(user_id,order_id,goods_id)" +
            "VALUES" +
            "(#{userId},#{orderId},#{goodsId})")
    int createMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("SELECT * FROM order_info WHERE id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);
}
