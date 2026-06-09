package com.itbaizhan.controller;

import com.itbaizhan.service.AiAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMemoryController {

    @Autowired
    AiAssistant assistant;

    /**
     * 聊天
     * @param message
     * @return
     */
    @GetMapping("/chat")
    public String chat(Integer userId,String message){
        String chat = assistant.chat(userId, message);
        return chat;
    }


}
