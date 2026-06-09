package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主启动类
 */
@Slf4j
@SpringBootApplication
public class ChatApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ChatApplication.class,args);
        log.info("************* ChatApplication success ***********");
    }
}
