package org.wangep.webflux;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/***
 * created by wange on 2020/4/27 16:47
 */
@RestController
@RequestMapping("/api")
public class IndexController {

    @GetMapping("/**")
    public Mono<Object> getEntry(ServerWebExchange exchange,
                                 @RequestParam Map<String, Object> paramsMap,
                                 @RequestHeader Map<String, Object> headsMap) {

        return Mono.just("success");
    }

}
