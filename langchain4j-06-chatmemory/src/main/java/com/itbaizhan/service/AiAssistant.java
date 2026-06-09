package com.itbaizhan.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface AiAssistant {


    /**
     * 聊天
     * @param userId 用户id （根据id进行隔离记忆）
     * @param message 消息
     * @return
     */
    String chat(@MemoryId Integer userId,@UserMessage String message);


}
