package com.example.demo.common.Config;

import com.example.demo.common.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 LoginInterceptor 拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**");  // 拦截所有路径，依赖过滤器决定是否拦截请求
    }
}
