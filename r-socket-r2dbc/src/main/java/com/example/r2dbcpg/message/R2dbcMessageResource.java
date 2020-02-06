package com.example.r2dbcpg.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Log4j2
@Controller
@RequiredArgsConstructor
public class R2dbcMessageResource {

    private final Flux<Message> r2dbcMessageStream;
    private final Consumer<Message> onR2dbcMessageHandler;
    private final R2dbcMessageRepository messageRepository;

    @MessageMapping("stream-messages")
    Flux<Message> stream() {
        return r2dbcMessageStream.doOnNext(m -> log.info("stream {} message.", m));
    }

    @MessageMapping("find-messages")
    Flux<Message> find() {
        return messageRepository.findAll()
                                .doOnNext(m -> log.info("message {} found.", m));
    }

    @MessageMapping("save-message")
    Mono<Message> save(@RequestBody Message message) {
        return messageRepository.save(message)
                                .doOnSuccess(onR2dbcMessageHandler)
                                .doOnNext(m -> log.info("message {} saved.", m));
    }
}
