package ru.otus.spring.hw.kanban.config;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw.kanban.exceptions.BoardNotFoundException;

@Component
@Order(-2)
// ref: https://github.com/hantsy/spring-reactive-sample/blob/master/boot-exception-handler/src/main/java/com/example/demo/DemoApplication.java
class RestWebExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof BoardNotFoundException) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);

            // marks the response as complete and forbids writing to it
            return exchange.getResponse().setComplete();
        }
        return Mono.error(ex);
    }
}