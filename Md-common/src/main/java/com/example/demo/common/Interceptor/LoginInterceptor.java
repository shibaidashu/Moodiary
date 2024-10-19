package com.example.demo.common.Interceptor;

import com.example.demo.common.Utils.ThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取解析后的用户信息
        String userId = request.getHeader("user-info");
        if (userId != null && !userId.isEmpty()) {
            // 直接将用户信息存储到 ThreadLocal 中，方便后续使用
            ThreadLocalUtil.set(userId);
        }
        return true;  // 无论是否有用户信息，继续放行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除 ThreadLocal 中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
