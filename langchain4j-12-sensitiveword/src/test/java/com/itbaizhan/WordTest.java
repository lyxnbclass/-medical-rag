package com.itbaizhan;

import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import org.junit.jupiter.api.Test;

public class WordTest {

    @Test
    public void word(){
        String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";
        // 判断是否有敏感词
        System.out.println(SensitiveWordHelper.contains(text));
        // 对字符串进行过敏
        System.out.println(SensitiveWordHelper.replace(text));
        System.out.println(SensitiveWordHelper.replace(text,'0'));
        // 获取敏感词
        System.out.println(SensitiveWordHelper.findFirst(text));
        System.out.println(SensitiveWordHelper.findAll(text));

    }


}
