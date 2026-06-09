package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class FuncationApplication {
    public static void main(String[] args) {
        SpringApplication.run(FuncationApplication.class,args);
        log.info("FuncationApplication success");
    }
}
