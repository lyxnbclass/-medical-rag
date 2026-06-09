package com.itbaizhan;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 医疗问答主启动类
 */
@Slf4j
@EnableScheduling//启动定时任务
@MapperScan("com.itbaizhan.mapper")
@SpringBootApplication
public class MedicalAppcation
{
    public static void main( String[] args )
    {
        SpringApplication.run(MedicalAppcation.class,args);
        log.info("********** 医疗问答系统启动成功 *********** ");
    }
}
