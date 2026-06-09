package com.itbaizhan;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.DocumentTransformer;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.document.splitter.DocumentBySentenceSplitter;
import dev.langchain4j.data.document.splitter.DocumentByWordSplitter;
import dev.langchain4j.data.segment.TextSegment;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class FileTest {

    @Test
    public void load() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test\\test-file.docx");
    }

    @Test
    public void url (){
        String url = "https://raw.githubusercontent.com/langchain4j/langchain4j/main/langchain4j/src/test/resources/test-file-utf8.txt";
        Document document = UrlDocumentLoader.load(url, new TextDocumentParser());
        System.out.println(document.text());
    }
    @Test
    public void find() throws FileNotFoundException {
        DocumentParser documentParser = new ApacheTikaDocumentParser();
        // 读取word
        FileInputStream word = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test\\test-file.docx");
        FileInputStream pdf = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test\\test-file.pdf");
        FileInputStream ppt = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test\\test-file.ppt");
        FileInputStream xls = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test\\test-file.xls");

        // 解析
        Document docword = documentParser.parse(word);
        Document docpdf = documentParser.parse(pdf);
        Document docppt = documentParser.parse(ppt);
        Document docxls = documentParser.parse(xls);

        // 输出
//        System.out.println(docword);
//        System.out.println(docpdf);
//        System.out.println(docppt);
//        System.out.println(docxls);
    }


    /**
     * 文本切分
     * @throws FileNotFoundException
     */
    @Test
    public void split() throws FileNotFoundException {

        // 1、读取文档 word
        ApacheTikaDocumentParser apacheTikaDocumentParser = new ApacheTikaDocumentParser();
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test\\面试宝典.doc");

        // 2、解析文档
        Document document = apacheTikaDocumentParser.parse(fileInputStream);

        // 3、切分段落
        DocumentByParagraphSplitter documentByParagraphSplitter = new DocumentByParagraphSplitter(200,30);

        // 4、切分
        List<TextSegment> split = documentByParagraphSplitter.split(document);

        for (TextSegment segment : split) {
            System.out.println(segment);
        }



    }


}
