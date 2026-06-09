package com.itbaizhan.config;

import com.itbaizhan.service.NumberService;
import com.itbaizhan.service.PojoService;
import com.itbaizhan.service.SentimentService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置
 */
@Configuration
public class LLMConfig {


    /**
     * 提供模型元信息
     *
     * @return
     */
    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                .modelName("qwen-max")
                .logRequests(true)
                .logResponses(true)
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }


    @Bean
    public SentimentService chatAssistant() {
        return AiServices.
                builder(SentimentService.class)
                // 选择模型
                .chatLanguageModel( chatLanguageModel() )
                .build();
    }

    @Bean
    public NumberService numberService(){
        return AiServices.
                builder(NumberService.class)
                // 选择模型
                .chatLanguageModel( chatLanguageModel() )
                .build();
    }

    @Bean
    public PojoService pojoService (){
        return AiServices.
                builder(PojoService.class)
                // 选择模型
                .chatLanguageModel( chatLanguageModel() )
                .build();
    }

}
