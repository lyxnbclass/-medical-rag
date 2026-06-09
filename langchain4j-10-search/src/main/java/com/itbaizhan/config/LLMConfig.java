package com.itbaizhan.config;

import com.itbaizhan.service.IChatAssistant;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.web.search.WebSearchTool;
import dev.langchain4j.web.search.searchapi.SearchApiWebSearchEngine;
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
    public IChatAssistant chatAssistant() {
        // web search
        SearchApiWebSearchEngine websearch = SearchApiWebSearchEngine.builder().
                apiKey("8xVNoRDnftHtMZXPHhmML2Ly")
                .engine("baidu")
                .build();
        return AiServices.
                builder(IChatAssistant.class)
                // 选择模型
                .chatLanguageModel( chatLanguageModel() )
                .tools(new WebSearchTool(websearch))
                .build();
    }


}
