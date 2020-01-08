package com.hours22.system_monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class GetSetService {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    

    public void test() {
        //hashmap같은 key value 구조
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set("jdkSerial", "jdk");
        String result = (String) vop.get("jdkSerial");
        System.out.println(result);//jdk
    }
}