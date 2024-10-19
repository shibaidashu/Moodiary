package com.example.demo.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class DefaultFeignConfig {

    @Bean
    public RequestInterceptor userInfoRequestInterceptor() {
        return template -> {
            // 获取当前请求上下文
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return; // 如果请求上下文不存在，直接返回
            }

            // 获取 HttpServletRequest 对象
            HttpServletRequest request = attributes.getRequest();

            // 从请求头中获取 user-info，即 userId
            String userId = request.getHeader("user-info");

            // 检查 userId 是否为null，避免空指针问题
            if (userId != null && !userId.isEmpty()) {
                // 将 userId 放入 Feign 请求头中，传递给下游微服务
                template.header("user-info", userId);
            }
        };
    }
}
