package com.itbaizhan.service;

import dev.langchain4j.agent.tool.Tool;

public class NumberHandler {


    /**
     * 返回给定数字的平方根
     * @param number
     * @return
     */
    @Tool("返回给定数字的平方根")
    public double square(double number) {
        return Math.sqrt(number);
    }

}
