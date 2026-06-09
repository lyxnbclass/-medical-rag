package com.itbaizhan.config;

import com.itbaizhan.service.IChatAssistant;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.zhipu.ZhipuAiEmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.router.DefaultQueryRouter;
import dev.langchain4j.rag.query.transformer.CompressingQueryTransformer;
import dev.langchain4j.rag.query.transformer.DefaultQueryTransformer;
import dev.langchain4j.rag.query.transformer.QueryTransformer;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

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


    //    @Bean
//    public IChatAssistant chatAssistant() {
//        return AiServices.
//                builder(IChatAssistant.class)
//                // 选择模型
//                .chatLanguageModel( chatLanguageModel() )
//                .build();
//    }
    @Bean
    public EmbeddingModel embeddingModel() {
        return ZhipuAiEmbeddingModel.builder()
                .apiKey(System.getenv("ZHIPU_API_KEY"))
                .callTimeout(Duration.ofSeconds(1000))
                .connectTimeout(Duration.ofSeconds(1000))
                .readTimeout(Duration.ofSeconds(1000))
                .writeTimeout(Duration.ofSeconds(1000))
                .build();
    }

    /**
     * 存储向量地方
     *
     * @return
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return QdrantEmbeddingStore.builder()
                .host("192.168.47.120")
                .port(6334)
                .collectionName("itbaizhan")
                .build();
    }

    @Bean
    public IChatAssistant chatAssistant(ChatLanguageModel chatLanguageModel) {

        QueryTransformer queryTransformer = new CompressingQueryTransformer(chatLanguageModel);

        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore())
                .embeddingModel(embeddingModel())
                .maxResults(2)
                .minScore(0.6)
                .build();


        RetrievalAugmentor retrievalAugmentor = DefaultRetrievalAugmentor.builder()
                .queryTransformer(queryTransformer)
                .contentRetriever(contentRetriever)
                .build();


        return AiServices.builder(IChatAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                // 注意每个memoryId对应创建一个ChatMemory
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }

}
