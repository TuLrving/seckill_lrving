package com.show.seckill.config;

import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.redis.keys.MiaoshaUserKey;
import com.show.seckill.service.MiaoshaUserService;
import com.show.seckill.service.impl.MiaoshaUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 21:46
 * @Description: 具体的解析器，当参数包含MiaoshaUser这个类时执行解析方法，
 *              在解析方法中判断是否含有相应的cookie，
 *              若含有则通过cookie获取token，利用token从redis中获取MIaoshaUser对象
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> aClass = methodParameter.getParameterType();
        return aClass == MiaoshaUser.class;
    }

    /**
     * 从cookie中查询出token信息，然后利用token从redis数据库中查询出相应的MiaoshaUser对象
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request,MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN);

        if(StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return miaoshaUserService.getByToken(response,token);
    }

    /**
     * 查询相应的cookie
     * @param request
     * @param cookieNameToken
     * @return
     */
    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(cookieNameToken)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
