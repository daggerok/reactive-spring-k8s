package com.example.r2dbcpg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class R2dbcApplication {
    public static void main(String[] args) {
        // reactor.tools.agent.ReactorDebugAgent.init();
        // // reactor.blockhound.BlockHound.install();
        SpringApplication.run(R2dbcApplication.class, args);
    }
}
