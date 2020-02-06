package com.example.websocket.message;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Log4j2
@Configuration
class WebFluxRequesterConfig {

    @Bean
    Mono<RSocketRequester> rSocketRequester(RSocketRequester.Builder builder,
                                            @Value("${rsocketwebsocket.uri}") URI uri) {
        log.info(uri);
        return builder.connectWebSocket(uri)
                      .retryBackoff(2, Duration.ofSeconds(2));
    }
}
