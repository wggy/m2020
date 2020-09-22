package org.wangep.webflux;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

/***
 * created by wange on 2020/4/27 17:06
 */
@Component
public class UapLoginHandler {

    public Mono<ServerResponse> loginUrl(ServerRequest serverRequest) {
        String targetUrl = "https://www.baidu.com";
        return ServerResponse.temporaryRedirect(URI.create(targetUrl)).build();
    }
}
