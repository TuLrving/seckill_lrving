package com.show.seckill.controller;

import com.show.seckill.domain.MiaoshaOrder;
import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.rabbitmq.MQSender;
import com.show.seckill.rabbitmq.MiaoshaMessage;
import com.show.seckill.redis.RedisService;
import com.show.seckill.redis.keys.GoodsKey;
import com.show.seckill.result.CodeMsg;
import com.show.seckill.result.Result;
import com.show.seckill.service.GoodsService;
import com.show.seckill.service.MiaoshaService;
import com.show.seckill.service.OrderService;
import com.show.seckill.vo.GoodsVO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 涂成
 * @Date: 2019/6/23 20:56
 * @Description:
 */
@RequestMapping(value = "/miaosha")
@Controller
public class MiaoshaController implements InitializingBean {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    private Map<Long, Boolean> localMap = new HashMap<>();

    /**
     * 系统开启时将商品库存存入缓存中
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsVOS = goodsService.listGoods();
        if (goodsVOS == null) {
            return;
        }
        for (GoodsVO goodsVO : goodsVOS) {
            redisService.set(GoodsKey.getGoodsStockCount, "" + goodsVO.getId(), goodsVO.getStockCount());
            //false代表该商品未被秒杀完
//            if (goodsVO.getStockCount() > 0) {
////                localMap.put(goodsVO.getId(), false);
////            } else {
////                localMap.put(goodsVO.getId(), true);
////            }
        }
    }

    /**
     * 500 400
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(MiaoshaUser user,
                                   @RequestParam(value = "goodsId") Long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);//未登录0
        }
        //内存标记，判断是否已经秒杀完毕
//        Boolean isOver = localMap.get(goodsId);
//        if (isOver) {
//            return Result.error(CodeMsg.MIAO_SHA_OVER);
//        }

        //从缓存中预减库存
        Long decr = redisService.decr(GoodsKey.getGoodsStockCount, "" + goodsId);
        if (decr < 0) {
            //内存标记，标记该商品已经秒杀完毕
//            localMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //从缓存中查询，判断是否是重复秒杀
        MiaoshaOrder order = orderService.getMiaoshaOrderByCache(user.getId(), goodsId);
        if (order != null) {//存在重复秒杀情况
            return Result.error(CodeMsg.REPEAT_MIAO_SHA);
        }
        //没有以上情况，开始入队
        MiaoshaMessage message = new MiaoshaMessage(user, goodsId);
        mqSender.sendMiaoshaMsg(message);
        //开始排队
        return Result.success(0);
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(MiaoshaUser user, @RequestParam(value = "goodsId") Long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);//未登录
        }
        long result = miaoshaService.getMiaoshaOrderResult(user.getId(), goodsId);
        return Result.success(result);
    }

}
