package com.itbaizhan.config;


import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.zhipu.ZhipuAiImageModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 配置
 */
@Configuration
public class LLMConfig {

    /**
     * Duration callTimeout,
     * Duration connectTimeout,
     * Duration readTimeout,
     * Duration writeTimeout
     * @return
     */
    @Bean
    public ImageModel imageModel(){
        ImageModel build = ZhipuAiImageModel.builder()
                .apiKey(System.getenv("ZHIPU_API_KEY"))
                .connectTimeout(Duration.ofSeconds(10000))
                .callTimeout(Duration.ofSeconds(10000))
                .readTimeout(Duration.ofSeconds(10000))
                .writeTimeout(Duration.ofSeconds(10000))
                .logRequests(true)
                .logResponses(true)
                .build();

        return build;
    }


}
