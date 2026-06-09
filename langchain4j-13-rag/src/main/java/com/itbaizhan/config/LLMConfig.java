package com.itbaizhan.config;

import com.itbaizhan.service.IChatAssistant;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
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

    /**
     * 存储 内存
     * @return
     */
    @Bean
    public InMemoryEmbeddingStore<TextSegment> embeddingStore(){
        return new InMemoryEmbeddingStore<>();
    }


    @Bean
    public IChatAssistant chatAssistant() {
        return AiServices.
                builder(IChatAssistant.class)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore()))
                .chatLanguageModel( chatLanguageModel() )
                .build();
    }


}
