package com.itbaizhan.scheduling;

import cn.hutool.core.io.FileUtil;
import com.itbaizhan.domain.FileInfo;
import com.itbaizhan.domain.FileStatus;
import com.itbaizhan.service.FileInfoService;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class MedicalFileScheduling {

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore<TextSegment> textSegmentEmbeddingStore;

    /**
     * 每五秒执行一次任务
     */
//    @Scheduled(fixedRate = 5000)
    public void data() throws IOException {
        System.out.println("轮询任务执行中。。。。");
        // 查询所有知识体
        List<FileInfo> list = fileInfoService.list();
        if (!CollectionUtils.isEmpty(list)) {
            for (FileInfo fileInfo : list) {
                if (fileInfo.getStatus().equals(FileStatus.PEENDING.getStatus())) {
                    // 更新知识体数据
                    fileInfo.setStatus(FileStatus.PROCESSING.getStatus());
                    fileInfoService.updateById(fileInfo);
                    // 获取文件数据
                    processFileData(fileInfo);
                }
            }
        }
    }


    /**
     * CompletableFuture java8 支持异步编程和非阻塞操作
     *
     * @param file
     * @return
     */
    @Async
    public CompletableFuture<Void> processFileData(FileInfo file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getFilePath()));
        String line = null;
        List<String> chunk = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            chunk.add(line);
            if (chunk.size() >= 10) {
                // 处理块中的内容
                processChunk(chunk);
                // 清除当前的块
                chunk.clear();
            }
        }
        if (!CollectionUtils.isEmpty(chunk)) {
            processChunk(chunk);
        }
        bufferedReader.close();
        return CompletableFuture.completedFuture(null).thenRun(() -> {
            // 文件处理完成后的操作
            updateFileStatus(file);
        });
    }

    /**
     * 监控和回调
     * @param file
     */
    private void updateFileStatus(FileInfo file) {
        // 更新完成状态
        file.setStatus(FileStatus.COMPLETED.getStatus());
        fileInfoService.updateById(file);
    }

    /**
     * 处理一个文件块的内容
     * @param chunk
     */
    private void processChunk(List<String> chunk) {
        // parallelStream 方法会异步执行。 文件内容的读取和每行数据的嵌入计算都会在后台线程中执行
        // 而且每行数据都会由不同的线程进行处理。能够加速计算过程
        chunk.parallelStream().forEach(data -> {
            TextSegment from = TextSegment.from(data);
            // 文本转换为向量
            Embedding content = embeddingModel.embed(from).content();
            // 保存到向量数据库
            textSegmentEmbeddingStore.add(content, from);
        });
    }


//    public static void main(String[] args) throws IOException {
//
//        String filePath = "D:\\filedata\\Medical Oncology_QA_all.csv_bak";
//        // 开始时间
//        long startTime = System.currentTimeMillis();
//
////        List<String> medicalLineMessage = FileUtil.readLines( new File(filePath), "utf-8");
////        for (String s : medicalLineMessage) {
////            System.out.println(s);
////        }
//
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
//        String line = null;
//        List<String> chunk = new ArrayList<>();
//        while ((line = bufferedReader.readLine()) != null ){
//            chunk.add(line);
//
//            if (chunk.size() >= 10){
//                // 处理块中的内容
//                processChunk(chunk);
//                // 清除当前的块
//                chunk.clear();
//            }
//        }
//        if(!CollectionUtils.isEmpty(chunk)){
//            processChunk(chunk);
//        }
//        bufferedReader.close();
//        // 结束时间
//        Long endTime = System.currentTimeMillis();
//
//        // 没有使用286毫秒   使用后215
//        System.out.println("消耗时间: " + (endTime - startTime));
//
//    }




}
