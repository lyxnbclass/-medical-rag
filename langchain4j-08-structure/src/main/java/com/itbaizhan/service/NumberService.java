package com.itbaizhan.service;

import dev.langchain4j.service.UserMessage;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface NumberService {

    /**
     * 返回int数字
     * @param text
     * @return
     */
    @UserMessage("从{{it}}中提取一个数字")
    int extractInt(String text);

    @UserMessage("从{{it}}中提取一个长数字")
    long extractLong(String text);

    @UserMessage("从{{it}}中提取一个大整数")
    BigInteger extractBigInteger(String text);

    @UserMessage("从{{it}}中提取浮点数")
    float extractFloat(String text);

    @UserMessage("从{{it}}中提取一个双精度数")
    double extractDouble(String text);

    @UserMessage("从{{it}}中提取一个大小数")
    BigDecimal extractBigDecimal(String text);

}
