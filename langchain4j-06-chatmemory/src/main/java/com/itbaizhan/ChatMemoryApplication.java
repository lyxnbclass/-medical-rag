package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ChatMemoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatMemoryApplication.class,args);
        log.info("ChatMemory Application started");
    }
}
