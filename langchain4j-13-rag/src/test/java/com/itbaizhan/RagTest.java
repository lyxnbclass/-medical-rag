package com.itbaizhan;

import com.itbaizhan.service.IChatAssistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RagTest {

    @Autowired
    InMemoryEmbeddingStore<TextSegment> embeddingStore;

    @Autowired
    IChatAssistant iChatAssistant;

    @Test
    public void save(){
        // RAG 2个阶段： 索引和检索

        // 1、导入文档
        Document document = FileSystemDocumentLoader.loadDocument("C:\\Users\\Administrator\\Desktop\\test\\合同.doc");
        String text = document.text();
        System.out.println(text);
        // 2、存储
        EmbeddingStoreIngestor.ingest(document,embeddingStore);
        // 3、检索
        String chat = iChatAssistant.chat("合同总金额？");
        System.out.println(chat);

    }

}
