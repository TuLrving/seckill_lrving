package com.show.seckill.service;

import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 09:44
 * @Description:
 */
public interface MiaoshaUserService {

    /**
     * 通过id获取user对象
     *
     * @param id
     * @return
     */
    MiaoshaUser getUserById(Long id);

    /**
     * 登录
     *
     * @param loginVO
     * @return
     */
    String login(HttpServletResponse response, LoginVO loginVO);

    /**
     * 通过token从redis中获取MIaoshaUser对象
     *
     * @param token
     * @return
     */
    MiaoshaUser getByToken(HttpServletResponse response, String token);

    /**
     * 修改密码
     *
     * @param token
     * @param userId
     * @param formPass
     * @return
     */
    boolean updatePassword(String token, long userId, String formPass);
}
