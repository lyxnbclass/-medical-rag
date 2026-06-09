package com.itbaizhan.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface IChatAssistant {


    /**
     * 聊天
     * @param message
     * @return
     */
    @SystemMessage("你好，我是:公众号爆款标题创作大师，请告诉我文章主题或者关键词，我将为你提供10个引人注目的爆款标题。")
    @UserMessage("请回答以下问题:{{message}}")
    String chat(@V("message") String message);


}
