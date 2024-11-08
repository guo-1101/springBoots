package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//添加WebMvc的配置类
@Configuration    //只需要添加Configuration用于注册配置类，不需要其他任何注解，已经自动配置好了
//现在不需要了
//@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
//    add 快捷键
//    添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//                System.out.println("请求被拦截，拦截器");
//                System.out.println(HandlerInterceptor.super.preHandle(request, response, handler));
                return true;
            }
        });
    }
}
