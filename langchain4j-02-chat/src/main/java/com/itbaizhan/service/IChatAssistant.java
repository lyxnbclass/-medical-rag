package com.itbaizhan.service;

/**
 * AI 助手接口
 */
public interface IChatAssistant {

    /**
     * 聊天
     * @param message 信息
     * @return
     */
    String chat(String message);

}
