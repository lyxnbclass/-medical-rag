package com.itbaizhan.service;

import dev.langchain4j.service.UserMessage;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

public interface PojoService {

    @UserMessage("从{{it}}中提取有关某人的信息")
    Person extractPerson(String text);


    @Data
    @ToString
    class Person{
        // 名字
        private String name;
        // 年龄
        private int age;
        // 出生日期
        private LocalDate birthDate;
    }

}
