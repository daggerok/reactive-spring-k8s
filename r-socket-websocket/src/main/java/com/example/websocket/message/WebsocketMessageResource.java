package com.example.websocket.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Controller
@RequiredArgsConstructor
public class WebsocketMessageResource {

    private final Mono<RSocketRequester> rSocketRequester;

    @MessageMapping("stream-messages")
    Flux<Message> stream() {
        return rSocketRequester.flatMapMany(rr -> rr.route("stream-messages")
                                                    .data(Mono.empty())
                                                    .retrieveFlux(Message.class))
                               .doOnNext(m -> log.info("stream {} message.", m));
    }

    @MessageMapping("find-messages")
    Flux<Message> find() {
        return rSocketRequester.flatMapMany(rr -> rr.route("find-messages")
                                                    .data(Mono.empty())
                                                    .retrieveFlux(Message.class))
                               .doOnNext(m -> log.info("message {} found.", m));
    }

    @MessageMapping("save-message")
    Mono<Message> save(@RequestBody Message message) {
        return rSocketRequester.flatMap(rr -> rr.route("save-message")
                                                .data(Mono.just(message))
                                                .retrieveMono(Message.class))
                               .doOnNext(m -> log.info("message {} saved.", m));
    }
}
