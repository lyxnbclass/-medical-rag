package com.itbaizhan.controller;

import com.itbaizhan.service.NumberService;
import com.itbaizhan.service.PojoService;
import com.itbaizhan.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private SentimentService sentimentService;

    @Autowired
    private NumberService numberService;

    @Autowired
    private PojoService pojoService;

    /**
     * 聊天
     * @param message
     * @return
     */
    @GetMapping("/chat")
    public SentimentService.SentimentType chat(String message){
//        boolean sentiment = sentimentService.isSentiment(message);
//        return sentiment;

        SentimentService.SentimentType sentimentType = sentimentService.addSentiment(message);

        return sentimentType;
    }


    @GetMapping("/number")
    public int extractInt(String message){
        return numberService.extractInt(message);
    }


    @GetMapping("/getperson")
    public PojoService.Person extractPerson(String message){
        return pojoService.extractPerson(message);
    }

}
