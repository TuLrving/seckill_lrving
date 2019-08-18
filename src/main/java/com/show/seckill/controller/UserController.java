package com.show.seckill.controller;

import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.redis.RedisService;
import com.show.seckill.redis.keys.MiaoshaUserKey;
import com.show.seckill.result.Result;
import com.show.seckill.utils.DBUtil;
import com.show.seckill.utils.MD5Util;
import com.show.seckill.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 涂成
 * @Date: 2019/6/24 13:53
 * @Description:
 */
@RequestMapping(value = "/user")
@Controller
public class UserController {

    @Autowired
    RedisService redisService;

    //    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//
//    @Autowired
//    MQSender mqSender;
//
//    @RequestMapping(value = "/info")
//    @ResponseBody
//    public Result<MiaoshaUser> userInfo(MiaoshaUser miaoshaUser) {
//        logger.info("info: {}", miaoshaUser);
//        return Result.success(miaoshaUser);
//    }
//
//    @RequestMapping(value = "/rabbitmq")
//    @ResponseBody
//    public Result<String> rabbitmq(MiaoshaUser user) {
//        String message = "你好啊 helloworld";
//        mqSender.send(message);
//        return Result.success(message);
//    }
//
//    @RequestMapping(value = "/rabbitmq/topic")
//    @ResponseBody
//    public Result<String> rabbitmqTopic(MiaoshaUser user) {
//        String message = "涂家铭是个弟弟";
//        mqSender.sendTopic(message);
//        return Result.success(message);
//    }
//
//    @RequestMapping(value = "/rabbitmq/fanout")
//    @ResponseBody
//    public Result<String> rabbitmqFanout(MiaoshaUser user) {
//        String message = "涂家铭是个弟弟";
//        mqSender.sendFanout(message);
//        return Result.success(message);
//    }
//
//    @RequestMapping(value = "/rabbitmq/headers")
//    @ResponseBody
//    public Result<String> rabbitmqHeaders(MiaoshaUser user) {
//        String message = "涂家铭是个弟弟";
//        mqSender.sendHeaders(message);
//        return Result.success(message);
//    }
    @RequestMapping(value = "/test")
    @ResponseBody
    public Result test() throws Exception {
        int count = 5000;
        List<MiaoshaUser> users = new ArrayList<>(count);
        //生成用户
        for (int i = 0; i < count; i++) {
            MiaoshaUser user = new MiaoshaUser();
            user.setId(13000000000L + i);
            user.setLoginCount(1);
            user.setNickname("user" + i);
            user.setRegisterDate(new Date());
            user.setSalt("123456");
            user.setPassword(MD5Util.inputPassTODBPass("123456", user.getSalt()));
            users.add(user);
        }
        System.out.println("create user:" + users.size());
        //插入数据库
        Connection conn = DBUtil.getConn();
        String sql = "insert into miaosha_user(login_count, nick_name, register_date, salt, password, id)values(?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < users.size(); i++) {
            MiaoshaUser user = users.get(i);
            pstmt.setInt(1, user.getLoginCount());
            pstmt.setString(2, user.getNickname());
            pstmt.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
            pstmt.setString(4, user.getSalt());
            pstmt.setString(5, user.getPassword());
            pstmt.setLong(6, user.getId());
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        pstmt.close();
        conn.close();
        System.out.println("insert to db");
        //登录，生成token
        for (MiaoshaUser user : users) {
            String token = UUIDUtil.uuid();
            redisService.set(MiaoshaUserKey.token, token, user);
            System.out.println(user.getId() + "," + token);
        }
        System.out.println("over");
        return Result.success(0);
    }
}
