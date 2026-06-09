package com.itbaizhan.service;

import dev.langchain4j.service.UserMessage;

public interface SentimentService {

    /**
     * 是积极情绪
     * @param text
     * @return
     */
    @UserMessage("Does {{it}} have positive sentiment?")
    boolean isSentiment(String text);

    /**
     * 分析情绪  it
     * @param text
     * @return
     */
    @UserMessage("Analyze sentiment of {{it}}")
    SentimentType addSentiment(String text);


    enum SentimentType {
        // 积极的
        POSITIVE,
        // 消极的
        NEGATIVE,
        // 中性的
        NEUTRAL

    }



}
