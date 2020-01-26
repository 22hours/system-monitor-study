package com.hours22.system_monitor_ver11.db;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.vo.PcData;

import io.lettuce.core.MapScanCursor;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.StreamScanCursor;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.output.KeyValueStreamingChannel;
import io.lettuce.core.support.ConnectionPoolSupport;



@Controller    // This means that this class is a Controller
public class LettuceController {
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	DataService dss;
	
	public static RedisClient redisClient = null;
	public static StatefulRedisConnection<String, String> connection = null;
	public static RedisCommands<String, String> syncCmd = null;		// Sync용 command
	public static RedisAsyncCommands<String, String> asyncCmd = null;		// Async용 command

    @GetMapping(path="/add") // Map ONLY GET Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody String getAllUsers() {
        // This returns a JSON or XML with the users
        return "all";
    }
    
    @GetMapping(path="/connections/start")
    public @ResponseBody String getConnection() {
        // This returns a JSON or XML with the users
    	String conn1 = "redis://localhost:6379/0 ";
    	RedisURI redisUri = new RedisURI("localhost", 6379, 5, TimeUnit.SECONDS);
    	
    	try {
    		redisClient = RedisClient.create(redisUri);
    	} catch(Exception e) {
    		System.out.println(e);
    		return "Connection Exception Events";
    	}
    	 connection = redisClient.connect();
    	 syncCmd = connection.sync();
    	 asyncCmd = connection.async();
 		System.out.println("Connection Success!");
 		try {
 			String ret = syncCmd.ping();
 			System.out.println("6379]ping->"+ret);
 		}catch(Exception e){
 			System.out.println(e);
 		}
    	
 		return "Success Open";
    }
    
    //@GetMapping(path="/connections/hset")
    public Map<String,String> getConnectionHset(String key, String endTime) {
        // This returns a JSON or XML with the users
        JSONObject jsonObject = new JSONObject();
    	try {
    		// syncCmd.del(key);
    		// return: 신규 필드이면 true, 이미 있는 필드이면 false, false라고 실패한 것이 아님.
    		Boolean bool1 = syncCmd.hset(key, "powerStatus", "OFF");
    		System.out.println("hset "+key+" powerStatus -> "+"OFF" + bool1);
    		jsonObject.put("powerStatus", "OFF");
    		
    		Boolean bool2 = syncCmd.hset(key, "endTime", endTime);
    		System.out.println("hset "+key+" endTime -> "+endTime + bool2);
    		jsonObject.put("endTime", endTime);
    		
    	} catch (Exception e) {
    		System.out.println(e);
    		return jsonObject;
    	}
 		return jsonObject;
    }
    
    @GetMapping(path="/connections/hset2")
    public @ResponseBody String getConnectionHset2() {
        // This returns a JSON or XML with the users
    	String key = "user-3";
    	try {
    		// syncCmd.del(key);
    		// return: 신규 필드이면 true, 이미 있는 필드이면 false, false라고 실패한 것이 아님.
    		Boolean bool2 = syncCmd.hset(key, "language", "KOR-R");
    		System.out.println("hset "+key+" language english -> "+bool2);
    		Boolean bool3 = syncCmd.hset(key, "gender", "AAA");
    		System.out.println("hset "+key+" gender m -> "+bool3);
    	} catch (Exception e) {
    		System.out.println(e);
    		return "Connection Exception Events";
    	}
 		return "Success Hset";
    }
    
    //@GetMapping(path="/connections/hget/{keyMap}")
    public Map<String, String> getConnectionHget(String key) {
        // This returns a JSON or XML with the users
    	String [] value = new String[3];
    	
    	//최종 완성될 JSONObject 선언(전체)
        JSONObject jsonObject = new JSONObject();
       
    	try {
    		// return: 해당 필드가 없으면 null
    		value[0] = syncCmd.hget(key, "id");
    		System.out.println("hget "+key+" id -> "+value[0]);
    		jsonObject.put("id", value[0]);
    		
    		value[1] = syncCmd.hget(key, "cpuData");
    		System.out.println("hget "+key+" cpuData -> "+value[1]);
    		jsonObject.put("cpuData", value[1]);
    		
    		value[2] = syncCmd.hget(key, "ramData");
    		System.out.println("hget "+key+" ramData -> "+value[2]);
    		jsonObject.put("ramData", value[2]);
    		
    	} catch (Exception e) {
    		System.out.println(e);
    	}
 		return jsonObject;
    }
    
    //@GetMapping(path="/connections/hgetall")
    public String getConnectionHgetall(String key) {
    	String res = null;
    	try {
    		Map<String, String> map = syncCmd.hgetall(key);
    		res = ojm.writeValueAsString(map);
    		res = ojm.writerWithDefaultPrettyPrinter().writeValueAsString(map);
    		
    		System.out.println("hgetall "+key+" -> "+map);
    		// KeyValueStreamingChannel
    		Long count = syncCmd.hgetall(new KeyValueStreamingChannel<String, String>() {
    			@Override
    			public void onKeyValue(String key, String value) {
    				System.out.println("hgetall (KeyValueStreamingChannel) -> "+key+" "+value);
    			}
    		}, key);
    		System.out.println("hgetall (KeyValueStreamingChannel) -> "+count);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
 		return res;
    }
    
    
    @GetMapping(path="/connections/end")
    public @ResponseBody String getConnectionExit() {
        // This returns a JSON or XML with the users
    	
 		connection.close();
 		redisClient.shutdown();
 		System.out.println("Connection Exit!");
 		return "Success Exit";
    }
}