package com.example.r2dbcpg.message;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;

@Configuration
class R2dbcSuccessNotifierConfig {

    @Bean
    Scheduler r2dbcMessageScheduler() {
        return Schedulers.newSingle("r2dbcMessageScheduler");
    }

    @Bean
    FluxProcessor<Message, Message> r2dbcMessageProcessor() {
        return EmitterProcessor.create(/* Queues.SMALL_BUFFER_SIZE */);
    }

    @Bean
    Consumer<Message> onR2dbcMessageHandler(FluxProcessor<Message, Message> r2dbcMessageProcessor) {
        return r2dbcMessageProcessor::onNext;
    }

    @Bean
    Flux<Message> r2dbcMessageStream(Scheduler r2dbcMessageScheduler,
                                     FluxProcessor<Message, Message> r2dbcMessageProcessor) {

        return r2dbcMessageProcessor.onBackpressureBuffer(/* Queues.SMALL_BUFFER_SIZE */)
                                    .subscribeOn(r2dbcMessageScheduler)
                                    .publishOn(r2dbcMessageScheduler)
                                    .share();
    }
}
