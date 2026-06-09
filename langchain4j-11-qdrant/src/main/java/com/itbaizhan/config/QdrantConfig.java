package com.itbaizhan.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.zhipu.ZhipuAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


/**
 * 向量数据库配置文件
 */
@Configuration
public class QdrantConfig {

    /**
     * 创建Qdrant客户端
     * @return
     */
    @Bean
    public QdrantClient qdrantClient(){
        QdrantGrpcClient.Builder builder = QdrantGrpcClient.newBuilder(
                "192.168.47.120",
                6334,
                false);
        return new QdrantClient(builder.build());
    }

    /**
     * 存储向量地方
     * @return
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(){
        return QdrantEmbeddingStore.builder()
                .host("192.168.47.120")
                .port(6334)
                .collectionName("medical")
                .build();
    }

    /**
     * Embedding Model 向量模型，机器学习模型，核心功能 将高维数据映射到低维空间的技术。
     * 离散的，稀疏的数据转换连续的、密集的向量表示
     *             Duration callTimeout,
     *             Duration connectTimeout,
     *             Duration readTimeout,
     *             Duration writeTimeout
     * @return
     */
    @Bean
    public EmbeddingModel embeddingModel(){
        return ZhipuAiEmbeddingModel.builder()
                .apiKey(System.getenv("ZHIPU_API_KEY"))
                .callTimeout(Duration.ofSeconds(1000))
                .connectTimeout(Duration.ofSeconds(1000))
                .readTimeout(Duration.ofSeconds(1000))
                .writeTimeout(Duration.ofSeconds(1000))
                .build();
    }



}
