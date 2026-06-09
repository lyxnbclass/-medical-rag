package com.itbaizhan.service;

import reactor.core.publisher.Flux;

/**
 * AI 助手接口
 */
public interface IChatAssistant {

    /**
     * 聊天
     * @param message 信息
     * @return
     */
    Flux<String> chat(String message);

}
