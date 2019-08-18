package com.show.seckill.controller;

import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.redis.RedisService;
import com.show.seckill.redis.keys.GoodsKey;
import com.show.seckill.result.Result;
import com.show.seckill.service.GoodsService;
import com.show.seckill.service.MiaoshaUserService;
import com.show.seckill.vo.GoodsDetailVO;
import com.show.seckill.vo.GoodsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 16:46
 * @Description: 商品模块
 */
@RequestMapping(value = "/goods")
@Controller
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 4000线程 qts 262
     *
     * @param model
     * @param miaoshaUser
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(Model model, MiaoshaUser miaoshaUser,
                       HttpServletRequest request, HttpServletResponse response) {//MiaoshaUser 通过SpringMvc解析器解析获得
        //先从缓存中取
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            //缓存中有页面信息
            return html;
        }
        //获取所有的商品
        List<GoodsVO> goods = goodsService.listGoods();
        model.addAttribute("goodsList", goods);
        model.addAttribute("user", miaoshaUser);
//        return "goods_list";

        //缓存中没有页面信息，使用thymeleafViewResolver解析获得html页面
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", webContext);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail(Model model, MiaoshaUser user, @PathVariable(value = "goodsId") Long goodsId,
                         HttpServletRequest request, HttpServletResponse response) {
//        //先从缓存中取
//        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
//        if (!StringUtils.isEmpty(html)) {
//            //缓存中有页面信息
//            return html;
//        }
//        GoodsVO goods = goodsService.findGoodsVoById(goodsId);
//
//        long startTime = goods.getStartDate().getTime();
//        long endTime = goods.getEndDate().getTime();
//        long nowTime = System.currentTimeMillis();
//
//        int miaoshaStatus;
//        long remainSeconds;
//
//        if (startTime > nowTime) {//秒杀未开始，返回剩余时间
//            miaoshaStatus = 0;
//            remainSeconds = (startTime - nowTime) / 1000;
//        } else if (endTime < nowTime) {//秒杀已经结束
//            miaoshaStatus = 2;
//            remainSeconds = -1;
//        } else {
//            miaoshaStatus = 1;//秒杀进行中
//            remainSeconds = 0;
//        }
//        model.addAttribute("user", user);
//        model.addAttribute("goods", goods);
//        model.addAttribute("miaoshaStatus", miaoshaStatus);
//        model.addAttribute("remainSeconds", remainSeconds);
//
//        //缓存中没有页面信息，使用thymeleafViewResolver解析获得html页面
//        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", webContext);
//        if (!StringUtils.isEmpty(html)) {
//            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
//        }

        return "";
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVO> detail2(MiaoshaUser user, @PathVariable(value = "goodsId") Long goodsId) {

        GoodsVO goods = goodsService.findGoodsVoById(goodsId);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long nowTime = System.currentTimeMillis();

        int miaoshaStatus;
        long remainSeconds;

        if (startTime > nowTime) {//秒杀未开始，返回剩余时间
            miaoshaStatus = 0;
            remainSeconds = (startTime - nowTime) / 1000;
        } else if (endTime < nowTime) {//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 1;//秒杀进行中
            remainSeconds = 0;
        }

        GoodsDetailVO vo = new GoodsDetailVO();
        vo.setGoods(goods);
        vo.setMiaoshaStatus(miaoshaStatus);
        vo.setRemainSeconds(remainSeconds);
        vo.setUser(user);
        return Result.success(vo);
    }

}
