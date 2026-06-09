package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主启动类
 */
@Slf4j
@SpringBootApplication
public class StreamApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(StreamApplication.class,args);
        log.info("StreamApplication started");
    }
}
