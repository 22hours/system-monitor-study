package com.hours22.system_monitor_ver11.db;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.vo.PcData;
import com.hours22.system_monitor_ver11.controller.MobileController;
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
	MobileController mobile;
	
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
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
    
    public Map<String, String> GetAllTypeDataRedis(String t1, String t2) throws JsonProcessingException {
    	
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
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
    	    	System.out.println("Cursor 작동중 !!!");
    	    	String tkey = new String(cursor.next());
    	    	String kvalue = mc.getConnectionHgetall(tkey);
    	    	Map<String, String> tmpMap = ojm.readValue(kvalue, Map.class);
    	    	String type = tmpMap.get("type");
    	    	System.out.println("지금 type : "+type);
    	    	if(!type.equals(t1)) continue;
    	    	
    	        System.out.println("key data [" + ++i +"번째] = " + tkey +" "+ kvalue);
    	        System.out.println();
    	        
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
    	        if(t1.equals("CLASS")) {
    	        	Map<String, String> tmpmap = GetClassOnOffInfo(tmpMap.get("id"));
    	        	jsonTmpObj.put("cntOn", tmpmap.get("cntOn"));
    	        	jsonTmpObj.put("cntOff", tmpmap.get("cntOff"));
    	        }
    	        jsonArray.add(jsonTmpObj);
    	    }
    	} finally {
    		jsonObject.put(t2, jsonArray);
        	jsonObject.put("total", Integer.toString(i));
    	    redisConnection.close();
    	}
    	return jsonObject;
    }
    
    public Map<String, String> GetClassOnOffInfo (String className) throws JsonProcessingException {

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        int cntON = 0, cntOFF = 0;
        
    	RedisConnection redisConnection = null;
    	try {
    	    redisConnection = redisTemplate.getConnectionFactory().getConnection();
    	    ScanOptions options = ScanOptions.scanOptions().match("*").count(50).build();
    	    Cursor<byte[]> cursor = redisConnection.scan(options);
    	    while (cursor.hasNext()) {
    	    	System.out.println("Cursor 작동중 !!!");
    	    	String tkey = new String(cursor.next());
    	    	String kvalue = mc.getConnectionHgetall(tkey);
    	    	Map<String, String> tmpMap = ojm.readValue(kvalue, Map.class);
    	    	String type = tmpMap.get("type");
    	    	if(!type.equals("PC")) continue;
    	    	
    	    	String cid = tmpMap.get("classId");
    	    	System.out.println("지금 type : "+type);
    	    	
    	    	if(!cid.equals(className)) continue;
    	    	
    	    	String tmp = tmpMap.get("powerStatus");
    	    	System.out.println("여기에서 PowerStatus 는 "+tmp);
    	    	if(tmp.equals("ON")) {
    	    		cntON++;
    	    		System.out.println("cntOn 증가중 ! - " + cntON);
    	    	}
    	    	else {
    	    		cntOFF++;
    	    		System.out.println("cntOff 증가중 ! - " + cntOFF);
    	    	}
    	    } 
    	} finally {
    		System.out.println("Finished!");
    	}

        JSONObject jsonObject = new JSONObject();
    	jsonObject.put("cntOn", Integer.toString(cntON));
    	jsonObject.put("cntOff", Integer.toString(cntOFF));
    	return jsonObject;
    }
    
    public Map<String, String> ClassPcs (String className) throws JsonProcessingException {
    	// t1 : D401
    	// 2차원 초기화, PC[20][20], new PC();
    	int maxR = 0;
    	int maxC = 0;
    	PcData[][] PCs = new PcData[17][17];
    	for(int i=1; i<=15; i++) 
    		for(int j=1; j<=15; j++)
    			PCs[i][j] = new PcData();
    	
    	
    	// type == pc && classId == D401
    	//		JSON -> POJO 2차원(i, j)
    
        int cnt = 0;
    	Map<String, String> arrList = new HashMap<String, String>();
    	
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        
    	RedisConnection redisConnection = null;
    	try {
    	    redisConnection = redisTemplate.getConnectionFactory().getConnection();
    	    ScanOptions options = ScanOptions.scanOptions().match("*").count(50).build();
    	    Cursor<byte[]> cursor = redisConnection.scan(options);
    	    while (cursor.hasNext()) {
    	    	System.out.println("Cursor 작동중 !!!" + ++cnt);
    	    	String tkey = new String(cursor.next());
    	    	String kvalue = mc.getConnectionHgetall(tkey);
    	    	Map<String, String> tmpMap = ojm.readValue(kvalue, Map.class);
    	    	String type = tmpMap.get("type");
    	    	if(!type.equals("PC")) continue;
    	    	
    	    	String cid = tmpMap.get("classId");
    	    	System.out.println("지금 type : "+type);
    	    	
    	    	if(!cid.equals(className)) continue;
    	    	
    	    	
    	    	
    	    	int r = Integer.parseInt(tmpMap.get("posR"));
    	    	int c = Integer.parseInt(tmpMap.get("posC"));
    	    	
    	    	if(maxR < r) maxR = r;
    	    	if(maxC < c) maxC = c;
    	    	
    	    	String id = tmpMap.get("id");
    	    	String powerStatus = tmpMap.get("powerStatus");
    	    	String classid = tmpMap.get("classId");
    	    	String name = tmpMap.get("name");
    	    	String cpuData = tmpMap.get("cpuData");
    	    	String ramData = tmpMap.get("ramData");
    	    	String startTime = tmpMap.get("startTime");
    	    	String endTime = tmpMap.get("endTime");
    	    	String posR = tmpMap.get("posR");
    	    	String posC = tmpMap.get("posC");
    	        PCs[r][c].setAllData(classid, powerStatus, id, name, cpuData, ramData, startTime, endTime, posR, posC, type); 
    	        
    	    
    	    	}
    	    } finally {
    		System.out.println("Finished!");
    	}
    	
    	
    	
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
    	
    	// POJO2차원 -> JSON2차원
    	for(int i=1; i<=maxR; i++) {

    		JSONArray jsonRowsArray = new JSONArray();
    		for(int j=1; j<=maxC; j++) {

    			String pR = PCs[i][j].getPosR();
    			String pC = PCs[i][j].getPosC();
    			System.out.println(pR + " / " + pC);
    			JSONObject tmpObject = new JSONObject();
    			tmpObject.put("classId", PCs[i][j].getClassId());
    			tmpObject.put("powerStatus", PCs[i][j].getPowerStatus());
    			tmpObject.put("id", PCs[i][j].getId());
    			tmpObject.put("name", PCs[i][j].getName());
    			tmpObject.put("cpuData", PCs[i][j].getCpuData());
    			tmpObject.put("ramData", PCs[i][j].getRamData());
    			tmpObject.put("startTime", PCs[i][j].getStartTime());
    			tmpObject.put("endTime", PCs[i][j].getEndTime());
    			tmpObject.put("posR", PCs[i][j].getPosR());
    			tmpObject.put("posC", PCs[i][j].getPosC());
    			tmpObject.put("type", PCs[i][j].getType());
    			jsonRowsArray.add(tmpObject);
    		}
    		jsonArray.add(jsonRowsArray);
    	}
    
    	// maxR, maxC, classId
    	jsonObject.put("pcs", jsonArray);
    	jsonObject.put("maxR", Integer.toString(maxR));
    	jsonObject.put("maxC", Integer.toString(maxC));
    	return jsonObject;
    }
    
    public void PostClassPcsPowerOff(WebRequest req, HttpServletRequest request, HttpServletResponse response, Map<String, String> reqMap) throws IOException, InterruptedException {
    	
    	
        String tclassId = reqMap.get("id");
        String tEndTime = reqMap.get("endTime");
        String tPStatus = reqMap.get("powerStatus");
        
    	int i = 0;
    	
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        
    	RedisConnection redisConnection = null;
    	try {
    	    redisConnection = redisTemplate.getConnectionFactory().getConnection();
    	    ScanOptions options = ScanOptions.scanOptions().match("*").count(50).build();
    	    Cursor<byte[]> cursor = redisConnection.scan(options);
    	    while (cursor.hasNext()) {
    	    	System.out.println("Cursor 작동중 !!!");
    	    	String tkey = new String(cursor.next());
    	    	String kvalue = mc.getConnectionHgetall(tkey);
    	    	Map<String, String> tmpMap = ojm.readValue(kvalue, Map.class);
    	    	String type = tmpMap.get("type");
    	    	System.out.println("지금 type : "+type);
    	    	if(!type.equals("PC")) continue;
    	    	String cid = tmpMap.get("classId");
    	    	if(!cid.equals(tclassId)) continue;
    	    	
    	        System.out.println("key data [" + ++i +"번째] = " + tkey +" "+ kvalue);
    	        System.out.println();
    	        
    	        Date now = new Date();
    	        String nowEndTime = transFormat.format(now);
    	        //	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	        nowEndTime = nowEndTime.replace(" ", "-");
    	        nowEndTime = nowEndTime.replace(":", "-");
    	        System.out.println(tmpMap.get("id") + "는 강의실 전체동작! " + nowEndTime);
    	        tmpMap.put("endTime", tEndTime);
    	        tmpMap.put("powerStatus", tPStatus);
    	        
    	        mobile.PostPcPower(req, request, response, tmpMap);
    	        System.out.println(tmpMap.get("id") + "는 지금 변경되었음!(강의실전체동작)");
    	    }
    	} finally {
    		System.out.println("Finished !");
    	}
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