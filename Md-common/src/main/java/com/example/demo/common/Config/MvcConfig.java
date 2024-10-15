package com.example.demo.common.Config;

import com.example.demo.common.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 LoginInterceptor 拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")  // 拦截所有路径，具体可以根据需要调整
                .excludePathPatterns("/login", "/register");  // 排除不需要拦截的路径
    }
}
