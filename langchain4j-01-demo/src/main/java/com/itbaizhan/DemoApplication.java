package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主启动类
 */
@Slf4j
@SpringBootApplication
public class DemoApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(DemoApplication.class,args);
        log.info("************* demoapplication success ***********");
    }
}
