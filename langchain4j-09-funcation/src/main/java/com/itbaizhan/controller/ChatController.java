package com.itbaizhan.controller;

import com.itbaizhan.service.IChatAssistant;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    ChatLanguageModel chatLanguageModel;

    @Autowired
    IChatAssistant iChatAssistant; // ai 助手

    /**
     * 聊天
     * @param message
     * @return
     */
    @GetMapping("/chat")
    public String chat(String message){
        return chatLanguageModel.generate(message);
    }


    /**
     * 聊天
     * @param message
     * @return
     */
    @GetMapping("/assistant")
    public String chat2(String message){
        return iChatAssistant.chat(message);
    }



    /**
     * 聊天
     * @param message
     * @return
     */
    @GetMapping("/assistant3")
    public String chat3(String message){
        return iChatAssistant.chat(message);
    }


}
