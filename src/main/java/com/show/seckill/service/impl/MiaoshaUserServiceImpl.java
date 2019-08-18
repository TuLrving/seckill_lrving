package com.show.seckill.service.impl;

import com.show.seckill.dao.MiaoshaUserDao;
import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.exception.GlobalException;
import com.show.seckill.redis.RedisService;
import com.show.seckill.redis.keys.MiaoshaUserKey;
import com.show.seckill.result.CodeMsg;
import com.show.seckill.service.MiaoshaUserService;
import com.show.seckill.utils.MD5Util;
import com.show.seckill.utils.UUIDUtil;
import com.show.seckill.vo.LoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 09:46
 * @Description:
 */
@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "TOKEN";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    /**
     * 通过id获取user对象
     *
     * @param id
     * @return
     */
    @Override
    public MiaoshaUser getUserById(Long id) {
        //从缓存中取
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null) {
            return user;
        }
        //缓存中没有该对象数据，从数据库中取，并存入缓存中
        user = miaoshaUserDao.getUserById(id);
        if (user != null) {
            redisService.set(MiaoshaUserKey.getById, "" + id, user);
        }
        return user;
    }

    /**
     * 登录
     *
     * @param loginVO,HttpServletResponse
     * @return
     */
    @Override
    public String login(HttpServletResponse response, LoginVO loginVO) {
        if (loginVO == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getUserById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();//数据库中存的用户密码
        String dbSalt = user.getSalt();//每个用户存储的盐值
        String pass = MD5Util.formPassToDBPass(password, dbSalt);
        if (!dbPass.equals(pass)) {
            throw new GlobalException(CodeMsg.PASSWORD_WRONG);
        }
        //登录成功后，将用户信息存入redis中,cookie中存入redis中的key值
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    /**
     * 将user存入redis中，并将相应的key值存入cookie中
     *
     * @param response
     * @param user
     */
    public void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());//最大有效时间，和redis有效时间一致
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 通过token值在redis中获取MiaoshaUser对象
     *
     * @param token
     * @return
     */
    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (miaoshaUser != null) {
            addCookie(response, token, miaoshaUser);
        }
        return miaoshaUser;
    }

    public boolean updatePassword(String token, long userId, String formPass) {
        //先从缓存中取出对象
        MiaoshaUser user = getUserById(userId);
        if (user == null) {
            //电话号码不存在
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        MiaoshaUser updatedUser = new MiaoshaUser();
        updatedUser.setId(userId);
        updatedUser.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        int i = miaoshaUserDao.updatePass(updatedUser);
        //更新缓存 数据
        redisService.delete(MiaoshaUserKey.getById, "" + userId);
        user.setPassword(updatedUser.getPassword());
        redisService.set(MiaoshaUserKey.token, "" + token, user);
        return i > 0;
    }
}
