package com.example.demo.config;

import com.example.demo.common.Utils.ThreadLocalUtil;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public RequestInterceptor userInfoRequestInterceptor() {
        return template -> {
            // 从 ThreadLocal 中获取用户信息（由拦截器存入）
            String userInfo = ThreadLocalUtil.get();

            if (userInfo == null) {
                // 如果为空则直接跳过
                return;
            }

            // 如果不为空则将 user-info 放入请求头中，传递给下游微服务
            template.header("user-info", userInfo);
        };
    }

}