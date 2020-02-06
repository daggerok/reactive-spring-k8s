package com.example.websocket.message;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Log4j2
@Configuration
class WebsocketRequesterConfig {

    @Bean
    Mono<RSocketRequester> rSocketRequester(RSocketRequester.Builder builder,
                                            @Value("${rsocketr2dbc.host}") String host,
                                            @Value("${rsocketr2dbc.port}") Integer port) {

        log.info("{}:{}", host, port);
        return builder.connectTcp(host, port)
                      .retryBackoff(2, Duration.ofSeconds(2));
    }
}
