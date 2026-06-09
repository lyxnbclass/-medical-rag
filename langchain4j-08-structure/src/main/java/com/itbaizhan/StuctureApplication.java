package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class StuctureApplication {
    public static void main(String[] args) {
        SpringApplication.run(StuctureApplication.class,args);
        log.info("***************** success **********");

    }
}
