package com.hours22.system_monitor_ver11.db;

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

import com.hours22.system_monitor_ver11.vo.PcData;

@Service
public class DataService {
	
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void test() {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        PcData data = new PcData();
        data.set_id("123");
        data.set_name("jjongwuner PC");
        valueOperations.set("jjongwuner", data);

        PcData data2 = (PcData)valueOperations.get("jjongwuner");
        System.out.println(data2);
    }
    
    public void RedisSave_ObjToJson(PcData tpc) {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        
        valueOperations.set(tpc.get_id(), tpc);

        PcData data2 = (PcData)valueOperations.get(tpc.get_id());
        System.out.println(data2);
    }
    
    public PcData RedisLoad_JsonToObj(String idx) {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();

        PcData data2 = (PcData)valueOperations.get(idx);
        System.out.println(data2);
        return data2;
    }
}