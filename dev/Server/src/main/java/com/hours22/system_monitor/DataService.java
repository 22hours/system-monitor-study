package com.hours22.system_monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void test() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        PcData data = new PcData();
        data.set_id("123");
        data.set_name("jjongwuner PC");
        valueOperations.set("jjongwuner", data);

        PcData data2 = (PcData)valueOperations.get("jjongwuner");
        System.out.println(data2);
    }
}