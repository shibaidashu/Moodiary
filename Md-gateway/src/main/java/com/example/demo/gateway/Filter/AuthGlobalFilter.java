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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取Request对象
        ServerHttpRequest request = exchange.getRequest();
        // 从请求头中获取Authorization的token
        String token = null;
        List<String> headers = request.getHeaders().get("Authorization");
        if (headers != null && !headers.isEmpty()) {
            token = headers.get(0);
        }

        // 如果没有token，直接返回401
        if (token == null || token.isEmpty()) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 验证token并解析数据
        Map<String, Object> claims;
        try {
            claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            // 如果token无效，返回401
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 将用户信息传递到请求头中
        String userId = claims.get("userId").toString();
        ServerWebExchange ex = exchange.mutate()
                .request(b -> b.header("user-info", userId))
                .build();

        // 放行
        return chain.filter(ex);
    }

    @Override
    public int getOrder() {
        return 0; // 确保过滤器优先执行
    }
}
