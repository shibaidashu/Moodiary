package com.example.demo.gateway.Filter;

import com.example.demo.gateway.Utils.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    // 登录、注册等不需要 token 验证的接口路径白名单
    private static final List<String> whiteListedPaths = List.of("/users/login", "/users/register");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 如果请求路径在白名单中，不进行 token 验证，直接转发
        if (isWhiteListed(path)) {
            return chain.filter(exchange);
        }

        // 获取 token
        String token = null;
        List<String> headers = request.getHeaders().get("user-info");
        if (headers != null && !headers.isEmpty()) {
            token = headers.get(0);
        }

        // 如果没有 token，返回 401 未授权
        if (token == null || token.isEmpty()) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 验证 token 并解析数据
        Map<String, Object> claims;
        try {
            claims = JwtUtil.parseToken(token);

            // 如果解析出来的 claims 为空，记录日志并返回 401
            if (claims == null) {
                throw new RuntimeException("Token claims are null");
            }
        } catch (Exception e) {
            // 如果 token 无效或解析错误，返回 401
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 将解析出的用户信息附加到请求头，传递给下游服务
        String userId = claims.get("id") != null ? claims.get("id").toString() : null;

        // 如果 userId 为空，返回 401
        if (userId == null) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 将 userId 添加到请求头，继续传递给下游服务
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("user-info", userId)  // 将用户 ID 附加到请求头
                .build();

        // 用修改后的请求继续处理
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    // 检查请求路径是否在白名单中
    private boolean isWhiteListed(String path) {
        return whiteListedPaths.stream().anyMatch(path::startsWith);
    }

    @Override
    public int getOrder() {
        return 0; // 确保过滤器优先执行
    }
}
