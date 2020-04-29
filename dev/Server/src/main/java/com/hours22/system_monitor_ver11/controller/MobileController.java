package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.client.ClientInfoController;
import com.hours22.system_monitor_ver11.client.ServerTimer;
import com.hours22.system_monitor_ver11.db.DataService;
import com.hours22.system_monitor_ver11.db.LettuceController;
import com.hours22.system_monitor_ver11.vo.PcData;

@Controller
public class MobileController{
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	LettuceController lc;
	
	@Autowired
	DataService dss;
	
	@Autowired
	ClientInfoController cic;
	
	@Autowired
	CacheController cache;
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	@CrossOrigin("*")
	@Async(value = "threadPoolMobileGetPcs")
	@RequestMapping(value = "/mobile/pc", method = RequestMethod.GET)
	public CompletableFuture<ResponseEntity<String>> GetPcData(WebRequest req, HttpServletRequest request, HttpServletResponse response) throws IOException {
			
		
		//if (req.checkNotModified(cache.GetCache())) {
		//	return null;
		//}
		
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /mobile/pc <- GET method [Client Ip : "+ cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
		
		//lc.getConnection();
		
		String json = ojm.writeValueAsString(dss.GetAllTypeDataRedis("PC", "pcs"));
	
		json = dss.PrettyPrinter(json);
		System.out.println(json);
		//lc.getConnectionExit();
		//cache.SetCache(req.getHeader("If-None-Match"));
		//System.out.println(req.getHeader("If-None-Match"));
		return CompletableFuture.completedFuture(ResponseEntity.ok().body(json));
	}
	
	
	@CrossOrigin("*")
	@Async(value = "threadPoolMobileGetPcData")
	@RequestMapping(value = "/mobile/pc/{id}/data", method = RequestMethod.GET)
	public CompletableFuture<ResponseEntity<String>> GetPcRamCpuData(WebRequest req, HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
		response.setContentType("application/json;charset=UTF-8"); 
		if (req.checkNotModified(cache.GetCache())) {
			return null;
		}
		
		//lc.getConnection();
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /mobile/pc/"+id+"/data <- GET method [Client Ip : "+ cic.getClientIp(request) +" ] at " + transFormat.format(new Date()));
		
		String json = lc.getConnectionHgetall(id);
		json = dss.PrettyPrinter(json);
		System.out.println(json);
		
		//response.getWriter().print(json);
		//lc.getConnectionExit();
		cache.SetCache(req.getHeader("If-None-Match"));
		return CompletableFuture.completedFuture(ResponseEntity.ok().body(json));
	}
	
	@CrossOrigin("*")
	@Async(value = "threadPoolMobilePowerOff")
	@RequestMapping(value = "/mobile/pc/power", method = RequestMethod.POST)
	public CompletableFuture<ResponseEntity<Map<String, String>>> PostPcPower(WebRequest req, HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) throws IOException, InterruptedException {
		response.setCharacterEncoding("UTF-8");

    	String id = map.get("id");
		String endTime = map.get("endTime");
    	
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /mobile/pc/"+id+"/power/"+endTime+" <- POST method [Client Ip : " +cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
	
    	Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
    	for(Thread thread : setOfThread){
    		System.out.println("Active Thread's [ Number : " +thread.getId()+" / Name : "+thread.getName()+" ] ");
    	    
    		String res = thread.getName();
    		if(res.equals("PCPowerOffMsg-Return-"+id)) {
    			thread.interrupt();
    			System.out.println("******"+res+" 스레드를 종료시킵니다.******");
    		}
    	}
    	
		//lc.getConnection();
		if(lc.getConnectionHkey(id) == false) {
			//lc.getConnectionExit();
			Map<String, String> jsonObject = new HashMap<String, String>();
			jsonObject.put("id", id);
			jsonObject.put("msg", "false");
			return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(jsonObject));
		}
		lc.getConnectionHset(id, map);
		//lc.getConnectionExit();
		
		System.out.println(map.toString());

		Map<String, String> jsonObject = new HashMap<String, String>();
    	jsonObject.put("id", id);
		jsonObject.put("endTime", endTime);
		jsonObject.put("msg", "true");
		cache.SetCache(req.getHeader("If-None-Match"));
		return CompletableFuture.completedFuture(ResponseEntity.ok().body(jsonObject));
	}
	
	@CrossOrigin("*")
	@Async(value = "threadPoolMobileLogin")
	@RequestMapping(value = "/mobile/login", method = RequestMethod.POST)
	public CompletableFuture<ResponseEntity<Map<String, String>>> GetAdminLogin(WebRequest req, HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) throws IOException, InterruptedException {
		Map<String, String> jsonObject = new HashMap<String, String>();
		
		//lc.getConnection();
		Map<String, String> auth = lc.getLoginSession(map);
		String type = auth.get("type");
		String ret = auth.get("msg");
		if(type.equals("admin") && ret.equals("false")) {
			//lc.getConnectionExit();
			jsonObject.put("msg", "false");
			return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(jsonObject));
		}
		//lc.getConnectionExit();
		auth.put("msg", "true");
		return CompletableFuture.completedFuture(ResponseEntity.ok().body(auth));
	}
	
	@CrossOrigin("*")
	@Async(value = "threadPoolMobileClass")
	@RequestMapping(value = "/mobile/class", method = RequestMethod.GET)
	public CompletableFuture<ResponseEntity<String>> GetAllClassData(WebRequest req, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		if (req.checkNotModified(cache.GetCache())) {
			return null;
		}
		
		//req.check
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /mobile/class <- GET method [Client Ip : "+ cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
		
		//lc.getConnection();
		
 		String json = ojm.writeValueAsString(dss.GetAllTypeDataRedis("CLASS", "classes"));
		json = dss.PrettyPrinter(json);
		System.out.println(json);
		
		//lc.getConnectionExit();
		cache.SetCache(req.getHeader("If-None-Match"));
		return CompletableFuture.completedFuture(ResponseEntity.ok().body(json));
	}
	
	@CrossOrigin("*")
	@Async(value = "threadPoolMobileClassId")
	@RequestMapping(value = "/mobile/class/{classId}", method = RequestMethod.GET)
	public CompletableFuture<ResponseEntity<String>> GetAllClassPcData(WebRequest req, HttpServletRequest request, HttpServletResponse response, @PathVariable String classId) throws IOException {
		
		if (req.checkNotModified(cache.GetCache())) {
			return null;
		}
		
		//req.check
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /mobile/class/"+classId+" <- GET method [Client Ip : "+ cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
		
		//lc.getConnection();
		
		String json = ojm.writeValueAsString(dss.ClassPcs(classId));
		json = dss.PrettyPrinter(json);
		System.out.println(json);
		
		//lc.getConnectionExit();
		cache.SetCache(req.getHeader("If-None-Match"));
		return CompletableFuture.completedFuture(ResponseEntity.ok().body(json));
	}
	
	@CrossOrigin("*")
	@Async(value = "threadPoolMobileClassPowerOff")
	@RequestMapping(value = "/mobile/class/power", method = RequestMethod.POST)
	public CompletableFuture<ResponseEntity<Map<String, String>>> PostAllClassPcPower(WebRequest req, HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) throws IOException, InterruptedException {
		String classId = map.get("id");
		
		//req.check
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /mobile/class/"+classId+"/power <- POST method [Client Ip : "+ cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
		
		
		
		//lc.getConnection();
		dss.PostClassPcsPowerOff(req, request, response, map);
		//PostPcPower(req, request, response, map);
		
		//lc.getConnectionExit();
		cache.SetCache(req.getHeader("If-None-Match"));
		map.put("message", "success update!");
		return CompletableFuture.completedFuture(ResponseEntity.ok().body(map));
	}
	
}