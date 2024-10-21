package com.example.demo.common.Interceptor;

import com.example.demo.common.Utils.ThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取解析后的用户信息
        String userId = request.getHeader("user-info");
        if (userId != null && !userId.isEmpty()) {
            // 将 userId 存储为 Map 而不是直接作为 String
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", userId);  // 假设 userId 是可以转换为 Integer 的
            ThreadLocalUtil.set(userInfo);
        }
        return true;  // 无论是否有用户信息，继续放行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除 ThreadLocal 中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
