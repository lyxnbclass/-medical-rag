package com.itbaizhan.config;

import com.itbaizhan.service.AiAssistant;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public AiAssistant aiAssistant() {
        return AiServices.builder(AiAssistant.class)
                // 会话模型
                .chatLanguageModel(chatLanguageModel())
                // 记忆缓存 MessageWindowChatMemory 滑动窗口 保留N最新消息并且驱逐不在适合的旧消息
                .chatMemoryProvider(  getChatMemoryProvider() )
                .build();
    }

    @Bean
    public ChatMemoryStore getChatMemoryStore() {
        return new PersistentChatMemoryStore();
    }

    @Bean
    public ChatMemoryProvider getChatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                // 设置消息窗口id
                .id(memoryId)
                // 设置最大消息条数 默认10
                .maxMessages(20)
                // 自定义持久化
                .chatMemoryStore(getChatMemoryStore())
                .build();

    }


}
