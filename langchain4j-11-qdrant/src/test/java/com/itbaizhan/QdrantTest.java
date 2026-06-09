package com.itbaizhan;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

@SpringBootTest
public class QdrantTest {

    @Autowired
    QdrantClient qdrantClient;

    @Autowired
    EmbeddingModel embeddingModel;

    @Autowired
    EmbeddingStore embeddingStore;

    /**
     * 创建索引
     */
    @Test
    public void  createCollections(){
        Collections.VectorParams build = Collections.VectorParams.newBuilder()
                // distance计算方法  Cosine余弦   Euclid欧式距离  Dot点积  Manhattan曼哈顿距离
                .setDistance(Collections.Distance.Cosine)
                // size 向量长度  数据维度
                .setSize(1024)
                .build();

        qdrantClient.createCollectionAsync("medical",build);
        System.out.println("创建成功");
    }

    /**
     * 存储文本到向量数据库
     */
    @Test
    public void saveText(){
        TextSegment from1 = TextSegment.from("客服的电话是400-3464563");
        TextSegment from2 = TextSegment.from("客服工作时间是周一到周五");
        TextSegment from3 = TextSegment.from("客服的投诉电话是400-123456");
        TextSegment from4 = TextSegment.from("客服的人数是245");
        // 转换向量
        Embedding content1 = embeddingModel.embed(from1).content();
        Embedding content2 = embeddingModel.embed(from2).content();
        Embedding content3 = embeddingModel.embed(from3).content();
        Embedding content4 = embeddingModel.embed(from4).content();
        // 存储入向量数据库
        embeddingStore.add(content1,from1);
        embeddingStore.add(content2,from2);
        embeddingStore.add(content3,from3);
        embeddingStore.add(content4,from4);
    }


    /**
     * 向量查询与过滤
     */
    @Test
    public void search(){
        // 问题
        String msg = "肝癌胆管癌晚期能做手术吗 男今年37岁，前不久被仔细检查出得了胆管癌";
        // 问题向量
        Embedding embedding = embeddingModel.embed(msg).content();
        // 搜索
        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                .maxResults(1)
                .queryEmbedding(embedding)
                .build();
        EmbeddingSearchResult search = embeddingStore.search(request);
        System.out.println(search.matches().get(0));
    }

    /**
     * 向量查询与过滤 元数据
     */
    @Test
    public void datameate(){
        TextSegment from1 = TextSegment.from("客服的女生人数是67人");
        from1.metadata().put("author","lisi");
        // 转换向量
        Embedding content1 = embeddingModel.embed(from1).content();
        // 存储入向量数据库
        embeddingStore.add(content1,from1);
        // 问题
        String msg = "你们公司客服有多少女生";
        // 问题向量
        Embedding embedding = embeddingModel.embed(msg).content();
        // 搜索
        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                .maxResults(1)
                // 过滤
                .filter(metadataKey("author").isEqualTo("lisi"))
                .queryEmbedding(embedding)
                .build();
        EmbeddingSearchResult search = embeddingStore.search(request);
        if (!CollectionUtils.isEmpty(search.matches())){
            List matches = search.matches();
            for (Object match : matches) {
                System.out.println(match);
            }
        }
    }

}
