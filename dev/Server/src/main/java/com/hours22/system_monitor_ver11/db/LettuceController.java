package com.hours22.system_monitor_ver11.db;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
public class LettuceController implements InitializingBean, DisposableBean {
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	DataService dss;
	
	private Object lock = new Object();
	public static RedisClient redisClient = null;
	public static StatefulRedisConnection<String, String> connection = null;
	public static RedisCommands<String, String> syncCmd = null;		// Sync�� command
	public static RedisAsyncCommands<String, String> asyncCmd = null;		// Async�� command

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
    
    
    public void getConnection() {
        // This returns a JSON or XML with the users
    	synchronized(lock) {
	    	String conn1 = "redis://localhost:6379/0 ";
	    	RedisURI redisUri = new RedisURI("localhost", 6379, 60, TimeUnit.SECONDS);
	    	
	    	try {
	    		redisClient = RedisClient.create(redisUri);
	    	} catch(Exception e) {
	    		
	    		System.out.println(e);
	    	}
	    	 connection = redisClient.connect();
	    	 syncCmd = connection.sync();
	    	 asyncCmd = connection.async();
	 		System.out.println("Redis Connection Success!");
	 		try {
	 			String ret = syncCmd.ping();
	 			System.out.println("6379]ping-> [connection start!]"+ret);
	 		}catch(Exception e){
	 			System.out.println(e);
	 		}
    	}
    }
    
    //@GetMapping(path="/connections/hset")
    /*
    public Map<String,String> getConnectionHsetPower(String key, String endTime) {
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
    */
    //@GetMapping(path="/connections/hset2")
    /*
    public void getConnectionHsetData(String key, Map<String, String> map) {
        // This returns a JSON or XML with the users
    	try {
    		// syncCmd.del(key);
    		// return: 신규 필드이면 true, 이미 있는 필드이면 false, false라고 실패한 것이 아님.
    		
    		//id
    		//name
    		//classId
    		//powerStatus
    		Boolean bool2 = syncCmd.hset(key, "cpuData", map.get("cpuData"));
    		System.out.println("hset "+key+" cpuData -> "+bool2);
    		Boolean bool3 = syncCmd.hset(key, "ramData", map.get("ramData"));
    		System.out.println("hset "+key+" ramData -> "+bool3);
    		Boolean bool4 = syncCmd.hset(key, "startTime", map.get("startTime"));
    		System.out.println("hset "+key+" startTime -> "+bool2);
    		Boolean bool5 = syncCmd.hset(key, "endTime", map.get("endTime"));
    		System.out.println("hset "+key+" endTime -> "+bool3);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
    */
    /*
    public void getConnectionHsetAllData(String key, Map<String, String> map) {
        // This returns a JSON or XML with the users
    	try {
    		// syncCmd.del(key);
    		// return: 신규 필드이면 true, 이미 있는 필드이면 false, false라고 실패한 것이 아님.
    		System.out.println("**** Hset All Data ****");
    		Boolean bool1 = syncCmd.hset(key, "id", map.get("id"));
    		System.out.println("hset "+key+" cpuData -> "+bool1);
    		Boolean bool2 = syncCmd.hset(key, "cpuData", map.get("cpuData"));
    		System.out.println("hset "+key+" cpuData -> "+bool2);
    		Boolean bool3 = syncCmd.hset(key, "ramData", map.get("ramData"));
    		System.out.println("hset "+key+" ramData -> "+bool3);
    		Boolean bool4 = syncCmd.hset(key, "startTime", map.get("startTime"));
    		System.out.println("hset "+key+" startTime -> "+bool4);
    		Boolean bool5 = syncCmd.hset(key, "endTime", map.get("endTime"));
    		System.out.println("hset "+key+" endTime -> "+bool5);
    		Boolean bool6 = syncCmd.hset(key, "powerStatus", map.get("powerStatus"));
    		System.out.println("hset "+key+" powerStatus -> "+bool6);
    		Boolean bool7 = syncCmd.hset(key, "name", map.get("name"));
    		System.out.println("hset "+key+" name -> "+bool7);
    		Boolean bool8 = syncCmd.hset(key, "classId", map.get("classId"));
    		System.out.println("hset "+key+" classId -> "+bool8);
    		System.out.println("**** Finished Hset ****");
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
    */
    public boolean getConnectionHset(String key, Map<String, String> map) {
        // This returns a JSON or XML with the users
    	try {
    		// syncCmd.del(key);
    		// return: parameter Map에 무언가 있으면 true, 없으면 false. false인 필드는 기존 필드값을 덮어쓴다..
    		System.out.println("**** Hset All Data [TESTING] ****");

    		// field : id
    		if(map.get("id") != null) {
    			syncCmd.hset(key, "id", map.get("id"));
    			System.out.println("hset " +key+ " id -> true");
    		} else {
    			System.out.println("hset " +key+ " id -> false");
    			return false;
    		}
    		
    		// field : cpuData
    		if(map.get("cpuData") != null) {
    			syncCmd.hset(key, "cpuData", map.get("cpuData"));
    			System.out.println("hset "+key+ " cpuData -> true");
    		} else {
    			System.out.println("hset "+key+ " cpuData -> false");
    		}
    		
    		// field : ramData
    		if(map.get("ramData") != null) {
    			syncCmd.hset(key, "ramData", map.get("ramData"));
    			System.out.println("hset "+key+ " ramData -> true");
    		} else {
    			System.out.println("hset "+key+ " ramData -> false");
    		}
    		
    		// field : startTime
    		if(map.get("startTime") != null) {
    			syncCmd.hset(key, "startTime", map.get("startTime"));
    			System.out.println("hset "+key+ " startTime -> true");
    		} else {
    			System.out.println("hset "+key+ " startTime -> false");
    		}
    		
    		// field : endTime
    		if(map.get("endTime") != null) {
    			syncCmd.hset(key, "endTime", map.get("endTime"));
    			System.out.println("hset "+key+ " endTime -> true");
    		} else {
    			System.out.println("hset "+key+ " endTime -> false");
    		}
    		
    		// field : powerStatus
    		if(map.get("powerStatus") != null) {
    			syncCmd.hset(key, "powerStatus", map.get("powerStatus"));
    			System.out.println("hset "+key+ " powerStatus -> true");
    		} else {
    			System.out.println("hset "+key+ " powerStatus -> false");
    		}
    		
    		// field : name
    		if(map.get("name") != null) {
    			syncCmd.hset(key, "name", map.get("name"));
    			System.out.println("hset "+key+ " name -> true");
    		} else {
    			System.out.println("hset "+key+ " name -> false");
    		}
    		
    		// field : classId
    		if(map.get("classId") != null) {
    			syncCmd.hset(key, "classId", map.get("classId"));
    			System.out.println("hset "+key+" classId -> true");
    		} else {
    			System.out.println("hset "+key+" classId -> false");
    		}
    		
    		// field : type
    		if(map.get("type") != null) {
    			syncCmd.hset(key, "type", map.get("type"));
    			System.out.println("hset "+key+" type -> true");
    		} else {
    			System.out.println("hset "+key+" type -> false");
    		}
    		
    		if(map.get("posR") != null) {
    			syncCmd.hset(key, "posR", map.get("posR"));
    			System.out.println("hset "+key+" posR -> true");
    		} else {
    			System.out.println("hset "+key+" posR -> false");
    		}
    		
    		if(map.get("posC") != null) {
    			syncCmd.hset(key, "posC", map.get("posC"));
    			System.out.println("hset "+key+" posC -> true");
    		} else {
    			System.out.println("hset "+key+" posC -> false");
    		}
    		
    		System.out.println("**** Finished Hset ****");
    		return true;
    	} catch (Exception e) {
    		System.out.println(e);
    		return false;
    	}
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
    
    //@GetMapping(path="/pc/{key}")
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
    public boolean getConnectionHkey(String id) {
    	List<String> res = null;
    	res = syncCmd.keys(id);
    	System.out.println("여기 HKEY \n"+id + " "+res);
    	if(res.size() == 0) return false;
    	else return true;
    }
    
    public String getConnectionHgetField(String key, String field) {
    	String res = null;
    	res = syncCmd.hget(key, field);
    	return res;
    }
    
    public Map<String, String> getLoginSession(Map<String, String> map) throws JsonMappingException, JsonProcessingException {
    	Map<String, String> res = new HashMap<String, String>();
    	String id = map.get("id");
    	String pw = map.get("pw");
    	String resId = getConnectionHgetField(id, "id");
    	String resPw = getConnectionHgetField(id, "pw");
    	if(id.equals(resId) && pw.equals(resPw)) {
    		String ret = getConnectionHgetall(id);
    		res = ojm.readValue(ret, Map.class);
    		res.put("msg", "true");
    		return res;
    	}
    	res.put("msg", "false");
    	return res;
    }
    
    //@GetMapping(path="/connections/end")
    public void getConnectionExit() {
        // This returns a JSON or XML with the users
    	
    	synchronized(lock) {
	 		connection.close();
	 		redisClient.shutdown();
	 		System.out.println("Redis Connection Exit! [connection End]");
	 		//return "Success Exit";
    	}
    }

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		getConnectionExit();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		getConnection();
	}
}