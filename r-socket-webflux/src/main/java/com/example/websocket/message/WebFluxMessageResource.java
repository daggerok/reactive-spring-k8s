package com.example.websocket.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

import static org.springframework.http.MediaType.*;

@Log4j2
@RestController
@RequiredArgsConstructor
public class WebFluxMessageResource {

    private final Mono<RSocketRequester> rSocketRequester;

    @GetMapping(path = "/stream-messages", produces = {
            APPLICATION_STREAM_JSON_VALUE,
            TEXT_EVENT_STREAM_VALUE,
    })
    Flux<Message> stream() {
        return rSocketRequester.flatMapMany(rr -> rr.route("stream-messages")
                                                    .data(Mono.empty())
                                                    .retrieveFlux(Message.class))
                               .doOnNext(m -> log.info("stream {} message.", m));
    }

    @GetMapping("/find-messages")
    Flux<Message> find() {
        return rSocketRequester.flatMapMany(rr -> rr.route("find-messages")
                                                    .data(Mono.empty())
                                                    .retrieveFlux(Message.class))
                               .doOnNext(m -> log.info("message {} found.", m));
    }

    @PostMapping(path = "/save-message", consumes = APPLICATION_JSON_VALUE)
    Mono<Message> save(@RequestBody Message message) {
        return rSocketRequester.flatMap(rr -> rr.route("save-message")
                                                .data(Mono.just(message))
                                                .retrieveMono(Message.class))
                               .doOnNext(m -> log.info("message {} saved.", m));
    }

    @GetMapping("/**")
    Flux<String> fallback(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        Function<String, String> url = path -> String.format("%s://%s%s", uri.getScheme(), uri.getAuthority(), path);
        return Flux.just(
                String.format("_self: %s %s", request.getMethodValue(), uri.toString()),
                String.format("save message: POST %s", url.apply("/save-message")),
                String.format("find messages: GET %s", url.apply("/find-messages")),
                String.format("stream messages: GET %s", url.apply("/stream-messages"))
        );
    }
}
