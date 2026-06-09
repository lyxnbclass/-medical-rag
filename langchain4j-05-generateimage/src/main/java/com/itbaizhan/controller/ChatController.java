package com.itbaizhan.controller;


import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChatController {

    @Autowired
    ImageModel imageModel;

    /**
     * 生成图片
     * @return
     */
    @GetMapping("/generateImage")
    public String generateImage(String msg){
        Response<Image> generate = imageModel.generate(msg);
        return generate.content().url().toString();
    }

}
