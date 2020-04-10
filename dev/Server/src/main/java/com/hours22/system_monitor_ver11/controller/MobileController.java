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

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.client.ClientInfoController;
import com.hours22.system_monitor_ver11.client.ServerTimer;
import com.hours22.system_monitor_ver11.db.DataService;
import com.hours22.system_monitor_ver11.db.LettuceController;
import com.hours22.system_monitor_ver11.vo.PcData;

@Controller
@WebServlet(asyncSupported = true)
public class MobileController{
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	LettuceController lc;
	
	@Autowired
	DataService dss;
	
	@Autowired
	ClientInfoController cic;
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@CrossOrigin(origins="http://15.164.18.190:3000/")
	@RequestMapping(value = "/mobile/pc", method = RequestMethod.GET)
	public void GetPcData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8"); 
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /mobile/pc <- GET method [Client Ip : "+ cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
		
		lc.getConnection();
		
		String json = ojm.writeValueAsString(dss.GetAllPcDataRedis());
		json = dss.PrettyPrinter(json);
		System.out.println(json);
		
		lc.getConnectionExit();
		response.getWriter().print(json);
	}
	
	
	@RequestMapping(value = "/mobile/pc/new/", method = RequestMethod.GET)
	public @ResponseBody Callable<String> GetOnPCInstance(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException, ParseException {
		response.setContentType("application/json;charset=UTF-8"); 
		
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /mobile/pc/new <- GET method [Client Ip : "+ cic.getClientIp(request)+" ] at "+transFormat.format(new Date()));
		
		Thread ct = Thread.currentThread();
		ct.setName("Mobile-GetNewPc");
		System.out.println("현재 Thread [ ID : " + ct.getId()+" / Name : "+ct.getName()+" ] ");  
		System.out.println("[Mobile 새로운 PC 추가 method !");
		
		
		
		return new Callable<String>() {
            @Override
            public String call() throws Exception {
            	System.out.println("================================");
            	System.out.println("Mobile-GetNewPc Return 영역");
        		Thread.currentThread().setName("Mobile-GetNewPc-Return");
            	System.out.println("Thread ID : " + Thread.currentThread().getId());
            	System.out.println("Thread Name : " + Thread.currentThread().getName());
            	
            	
                boolean interrupted = false;
              
                while(true) {
                    try{
                    	Thread.sleep(5000);
                    }catch(InterruptedException e){
                    	System.out.println(Thread.currentThread().getName() +" Thread가 중지되었습니다.");
                    	interrupted = true;
                        break;
                    }
                }

                // end
                if(interrupted) {
                	Thread.sleep(2000);
                	String res = Thread.currentThread().getName();
                	String id = res.split("-")[2];
                	System.out.println("*******새로운 "+id+" PC가 켜졌습니다.********");
					String jsonObjectNew = lc.getConnectionHgetall(id);
					return jsonObjectNew;
                }
                else {
                	// 에러상황
	                return "";
                }
            }
        };
	}
	
	@CrossOrigin(origins="http://15.164.18.190:3000/")
	@RequestMapping(value = "/mobile/pc/{id}/data", method = RequestMethod.GET)
	public void GetPcRamCpuData(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
		response.setContentType("application/json;charset=UTF-8"); 
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		
		lc.getConnection();
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /mobile/pc/"+id+"/data <- GET method [Client Ip : "+ cic.getClientIp(request) +" ] at " + transFormat.format(new Date()));
		
		String json = ojm.writeValueAsString(dss.GetAllPcDataRedis());
		json = dss.PrettyPrinter(json);
		System.out.println(json);
		
		response.getWriter().print(json);
		lc.getConnectionExit();
	}
	
	@CrossOrigin(origins="http://15.164.18.190:3000/")
	@RequestMapping(value = "/mobile/pc/{id}/power/{endTime}", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> PostPcPower(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map, @PathVariable String id, @PathVariable String endTime) throws IOException, InterruptedException {
		response.setCharacterEncoding("UTF-8");

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
    	
		lc.getConnection();
		if(lc.getConnectionHkey(id) == false) {
			lc.getConnectionExit();
			Map<String, String> jsonObject = new HashMap<String, String>();
			jsonObject.put("id", id);
			jsonObject.put("msg", "false");
			return jsonObject;
		}
		lc.getConnectionHset(id, map);
		lc.getConnectionExit();
		
		System.out.println(map.toString());

		Map<String, String> jsonObject = new HashMap<String, String>();
    	jsonObject.put("id", id);
		jsonObject.put("endTime", endTime);
		jsonObject.put("msg", "true");
		return jsonObject;
	}
	
	@CrossOrigin(origins="http://15.164.18.190:3000/")
	@RequestMapping(value = "/mobile/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> GetAdminLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) throws IOException, InterruptedException {
		Map<String, String> jsonObject = new HashMap<String, String>();
		
		lc.getConnection();
		Map<String, String> auth = lc.getLoginSession(map);
		String type = auth.get("type");
		String ret = auth.get("msg");
		if(type.equals("admin") && ret.equals("false")) {
			lc.getConnectionExit();
			jsonObject.put("msg", "false");
			return jsonObject;
		}
		lc.getConnectionExit();
		return auth;
	}	
}