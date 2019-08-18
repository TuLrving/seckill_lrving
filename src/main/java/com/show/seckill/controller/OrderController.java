package com.show.seckill.controller;

import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.domain.OrderInfo;
import com.show.seckill.result.CodeMsg;
import com.show.seckill.result.Result;
import com.show.seckill.service.GoodsService;
import com.show.seckill.service.OrderService;
import com.show.seckill.vo.GoodsVO;
import com.show.seckill.vo.OrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: 涂成
 * @Date: 2019/6/26 22:34
 * @Description:
 */
@RequestMapping(value = "/order")
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public Result<OrderDetailVO> orderDetail(MiaoshaUser user, @RequestParam(value = "orderId") long orderId) {
        if (user == null) {
            //未登录
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            //订单不存在
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        GoodsVO goodsVO = goodsService.findGoodsVoById(order.getGoodsId());
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setGoods(goodsVO);
        orderDetailVO.setOrder(order);
        return Result.success(orderDetailVO);
    }

}
