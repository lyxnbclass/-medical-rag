package com.itbaizhan.controller;

import com.itbaizhan.service.IChatAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptController {

    @Autowired
    private IChatAssistant iChatAssistant;

    @GetMapping("/chat")
    public String chat(String message){
        return iChatAssistant.chat(message);
    }

}
