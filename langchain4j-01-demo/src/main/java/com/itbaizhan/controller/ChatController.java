package com.itbaizhan.controller;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    ChatLanguageModel chatLanguageModel;

    @GetMapping("/chat")
    public String chat(String message){
        String generate = chatLanguageModel.generate(message);
        return generate;
    }

}
