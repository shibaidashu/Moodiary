package com.example.demo.common.Interceptor;

import com.example.demo.common.Utils.ThreadLocalUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取 user-info
        String userInfo = request.getHeader("user-info");
        if (userInfo != null && !userInfo.isEmpty()) {
            // 将 userInfo 存储到 ThreadLocal 中，方便后续使用
            ThreadLocalUtil.set(userInfo);
            return true;
        } else {
            // 如果没有找到 user-info，返回 401 未授权
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除 ThreadLocal 中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
