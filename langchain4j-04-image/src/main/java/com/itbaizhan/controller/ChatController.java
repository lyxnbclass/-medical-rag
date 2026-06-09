package com.itbaizhan.controller;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@RestController
public class ChatController {

    @Autowired
    ChatLanguageModel chatLanguageModel;


    /**
     * 图片理解
     * @param file 文件
     * @param msg 描述
     * @return
     */
    @PostMapping("/generate")
    public String chat(MultipartFile file,String msg) throws IOException {
        // 获取文件流数据
        InputStream inputStream = file.getInputStream();
        // 转字节数组
        byte[] bytes = inputStream.readAllBytes();
        // base64转换
        String s = Base64.getEncoder().encodeToString(bytes);
        UserMessage userMessage = UserMessage.from(
                // 描述信息
                TextContent.from(msg),
                // 图片数据
                ImageContent.from(s,"image/png")
        );
        Response<AiMessage> generate = chatLanguageModel.generate(userMessage);
        return generate.content().text();
    }


}
