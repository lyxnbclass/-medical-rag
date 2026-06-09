package com.itbaizhan.test;

import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.io.File;

public class MapDBTest {
    @Test
    public void insert(){

        // 1、创建一个db文件
        File dbFile = new File("mydb");
        // 2、创建DB
        DB db = DBMaker.fileDB(dbFile).make();
        // 3、创建一个hashmap
        HTreeMap<String, String> myMap = db.hashMap("myMap")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.STRING)
                .createOrOpen();
        // 4、在Map保存数据
        myMap.put("key1", "value1");
        myMap.put("key2", "value2");

        // 5、提交事务确保数据库被持久化到磁盘
        db.commit();
        // 6、关闭数据库连接
        db.close();

        // 7、重新打开一个连接
        DB make = DBMaker.fileDB(dbFile).make();
        // 8、创建map
        HTreeMap<String, String> myMap2 = make.hashMap("myMap")
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.STRING)
                .createOrOpen();
        // 9、从map中检索数据
        String s = myMap2.get("key1");
        System.out.println(s);


    }
}
