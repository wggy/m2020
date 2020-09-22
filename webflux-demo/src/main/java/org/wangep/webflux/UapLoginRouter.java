package org.wangep.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/***
 * created by wange on 2020/4/27 17:06
 */
@Component
public class UapLoginRouter {
    @Autowired
    private UapLoginHandler uapLoginHandler;


    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(RequestPredicates.GET("/api/redirect"), uapLoginHandler::loginUrl);
    }

    @Bean
    public RouterFunction<ServerResponse> helloXttblog() {
        return RouterFunctions.route(
                RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                request -> ServerResponse.ok().body(BodyInserters.fromObject("Hello www.xttblog")))
                .filter((serverRequest, handlerFunction) -> {
                    // 针对/hello 的请求进行过滤，然后在响应中添加一个Content-Type属性
                    return ServerResponse.status(HttpStatus.OK).header("Content-Type", "text/plain; charset=utf-8").build();
                });
    }
}
