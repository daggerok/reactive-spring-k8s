package com.example.r2dbcpg.message;

import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Log4j2
@Configuration
class TestData {

    @Bean
    ApplicationRunner init(R2dbcMessageRepository r2DbcMessageRepository) {
        LoremIpsum loremIpsum = LoremIpsum.getInstance();
        return arguments -> r2DbcMessageRepository.deleteAll().thenMany(
                Flux.range(0, 9)
                    .map(i -> Message.of(loremIpsum.getName(), loremIpsum.getParagraphs(1, 3)))
                    .flatMap(r2DbcMessageRepository::save))
                                                  .subscribe(log::info);
    }
}
