package com.show.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 21:39
 * @Description: 用于给SpringMvc中添加解析器，解析cookie中的内容
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    UserArgumentResolver userArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(userArgumentResolver);
    }

}
