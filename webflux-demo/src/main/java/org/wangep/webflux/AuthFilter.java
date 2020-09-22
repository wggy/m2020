package org.wangep.webflux;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/***
 * created by wange on 2020/4/27 16:53
 */
@Log4j2
@Component
public class AuthFilter implements WebFilter {

    private static final String TOKEN_KEY = "token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("[AuthFilter order(1)] 前置处理");

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (StringUtils.isEmpty(path) || !path.startsWith("/api")) {
            return returnTokenError(exchange, HttpStatus.FORBIDDEN);
        }

        String token = request.getQueryParams().getFirst(TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            token = request.getHeaders().getFirst(TOKEN_KEY);
        }
        if (StringUtils.isBlank(token)) {
            HttpCookie cookie = request.getCookies().getFirst(TOKEN_KEY);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        if (StringUtils.isBlank(token)) {
            return returnTokenError(exchange, HttpStatus.UNAUTHORIZED);
        }

        if (exchange.getRequest().getURI().getPath().equals("/api/login11")) {
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path("/api/redirect").build()).build());
        }

        return chain.filter(exchange).then(Mono.fromRunnable(() -> log.info("[AuthFilter] order(1) 后置处理")));

    }

    private Mono<Void> returnTokenError(ServerWebExchange exchange, HttpStatus httpStatus) {
        String denyHttpBody = JSONObject.toJSONString(httpStatus);
        log.info("denyHttpBody: {}", denyHttpBody);
        return returnTokenError(exchange, denyHttpBody);
    }

    private Mono<Void> returnTokenError(ServerWebExchange exchange, String denyHttpBody) {
        log.info("denyHttpBody: {}", denyHttpBody);
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(denyHttpBody.getBytes())));
    }
}
