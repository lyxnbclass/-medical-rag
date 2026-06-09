package com.itbaizhan.controller;

import com.itbaizhan.service.IChatAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamChatController {

    @Autowired
    IChatAssistant iChatAssistant; // ai 助手

    /**
     * 聊天
     * @param message
     * @return
     */
    @GetMapping("/assistant")
    public Flux<String> chat2(String message){
        return iChatAssistant.chat(message);
    }



}
