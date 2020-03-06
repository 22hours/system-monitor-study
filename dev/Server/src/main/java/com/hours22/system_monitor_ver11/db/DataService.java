package com.hours22.system_monitor_ver11.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.vo.PcData;
import com.hours22.system_monitor_ver11.db.LettuceController;

import io.lettuce.core.output.KeyValueStreamingChannel;

@Service
public class DataService {
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	DataService dss;
	
	@Autowired
	LettuceController mc;
	
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String PrettyPrinter(String s) {
    	s = s.replaceAll("\\\\r\\\\n", "");
    	System.out.println("erase \\\\r\\\\n");
    	s = s.replaceAll("\\\\\"", "\"");
    	System.out.println("erase \\\"");
    	s = s.replaceAll("\\\\n", ""); //   <--- FOR ubuntu
    	System.out.println("erase \\n");//  <--- FOR ubuntu
    	return s;
    }
    
    public void test() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        PcData data = new PcData();
        data.setId("123");
        data.setName("jjongwuner PC");
        valueOperations.set("jjongwuner", data);

        PcData data2 = (PcData)valueOperations.get("jjongwuner");
        System.out.println(data2);
    }
    
    public Map<String, String> GetAllPcDataRedis() throws JsonProcessingException {
    	
    	//���� �ϼ��� JSONObject ����(��ü)
        JSONObject jsonObject = new JSONObject();
        //person�� JSON������ ���� Array ����
        JSONArray jsonArray = new JSONArray();
        //person�� �Ѹ� ������ �� JSONObject ����
        JSONObject jsonInfo = new JSONObject();
        
    	int i = 0;
    	Map<String, String> arrList = new HashMap<String, String>();
    	
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        
    	RedisConnection redisConnection = null;
    	try {
    	    redisConnection = redisTemplate.getConnectionFactory().getConnection();
    	    ScanOptions options = ScanOptions.scanOptions().match("*").count(50).build();
    	    Cursor<byte[]> cursor = redisConnection.scan(options);
    	    while (cursor.hasNext()) {
    	    	String tkey = new String(cursor.next());
    	    	//String kvalue = ojm.writeValueAsString((PcData)valueOperations.get(tkey));
    	    	String kvalue = mc.getConnectionHgetall(tkey);
    	        System.out.println("key data [" + ++i +"번째] = " + tkey +" "+ kvalue); // ��ȸ�� Key�� �̸��� ���
    	        System.out.println();
    	        //arrList.put(Integer.toString(i), kvalue);
    	        
    	        // String -> JsonParser
    	        JSONParser parser = new JSONParser();
    	        Object obj = null;
				try {
					obj = parser.parse(kvalue);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        JSONObject jsonTmpObj = (JSONObject) obj;
    	        
    	        jsonArray.add(jsonTmpObj);
    	    }
    	} finally {
    		jsonObject.put("pcs", jsonArray);
        	jsonObject.put("total", Integer.toString(i));
    	    redisConnection.close();
    	}
    	return jsonObject;
    }
    
    
    
    public void RedisSave_ObjToJson(PcData tpc) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        
        valueOperations.set(tpc.getId(), tpc);

        PcData data2 = (PcData)valueOperations.get(tpc.getId());
        System.out.println(data2);
    }
    
    public PcData RedisLoad_JsonToObj(String idx) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        PcData data2 = (PcData)valueOperations.get(idx);
        System.out.println(data2);
        return data2;
    }
    
}