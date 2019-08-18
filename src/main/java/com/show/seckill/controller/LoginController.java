package com.show.seckill.controller;

import com.show.seckill.result.Result;
import com.show.seckill.service.MiaoshaUserService;
import com.show.seckill.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 09:26
 * @Description:
 */
@RequestMapping(value = "/login")
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping(value = "/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/do_login")
    @ResponseBody
    public Result<String> login(HttpServletResponse response, @Valid LoginVO loginVO) {//valid校验
        String token = miaoshaUserService.login(response, loginVO);
        return Result.success(token);
    }
}
