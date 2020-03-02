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
	

	
	
	@RequestMapping(value = "/mobile/pc/{id}/power/{endTime}", method = RequestMethod.POST)
	public @ResponseBody Callable<Map<String, String>> PostPcPowerOff(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String endTime) throws IOException, InterruptedException, ParseException {
		
		response.setContentType("application/json;charset=UTF-8"); 

		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /pc/"+id+"/power/"+endTime+" <- POST method(언제꺼? v2.0) [Client Ip : " +cic.getClientIp(request)+" ] at "+transFormat.format(new Date()));
		System.out.println("현재 Thread ID : " + Thread.currentThread().getId());  
		
		lc.getConnection();
		String jsonStringForAndroid = lc.getConnectionHgetall(id); 
		System.out.println(jsonStringForAndroid);
		// to send android!
		// 어플이 먼저켜지고나서, 어플에서 long polling으로 업데이트.

		// 연장신청인지 언제꺼인지 판단
		// thread.name으로 찾고 있으면 interrupt(연장신청) opt == 2 
		//      ...         없으면 (언제꺼) opt == 1
    	Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
    	for(Thread thread : setOfThread){
    		System.out.println("Active Thread's [ Number : " +thread.getId()+" / Name : "+thread.getName()+" ] ");
    	    
    		String res = thread.getName();
    		if(res.equals("PCPowerOffExc-Return-"+id)) {
    			thread.interrupt();
    			System.out.println("******"+res+" 스레드를 종료시킵니다.******");
    		}
    	}
		
		Date now = new Date();
		String form = endTime;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println("Mobile에서 PC종료시간을 새로 설정하였습니다. (변경된 methd)" + transFormat.format(now));
		String [] seq = form.split("-");
		form = seq[0] + "-" + seq[1] + "-" + seq[2] +" "+seq[3] +":"+seq[4];
		System.out.println("종료예약 설정 [시간 : "+form+"]");
		
		Map<String, String> jsonObject = new HashMap<String, String>();
		jsonObject.put("id", id);
		jsonObject.put("powerStatus", "OFF");
		jsonObject.put("endTime", endTime);
		String jsonString = ojm.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
		
		//lc.getConnectionHsetAllData(id, jsonObject);
		lc.getConnectionHset(id,  jsonObject);
		
		// response
		System.out.println("hget 디버깅 결과 : " + jsonString);
		
		Date to = transFormat.parse(form);
		lc.getConnectionExit();
		
		return new Callable<Map<String, String>>() {
            @Override
            public Map<String, String> call() throws Exception {
            	System.out.println("================================");
            	System.out.println("PCPowerOffExc Return 영역");
        		Thread.currentThread().setName("PCPowerOffExc-Return-"+id);
            	System.out.println("Thread ID : " + Thread.currentThread().getId());
            	System.out.println("Thread Name : " + Thread.currentThread().getName());
            	
            	long MSG = ServerTimer.GetTime(endTime);
            	System.out.println(MSG / 1000 + "초 동안 Thread.sleep();");
            	System.out.println("Endtime is " + endTime);
                //Thread.sleep(252000);
                // start
                
            	long timeToSleep = MSG;
                long start, end, slept;
                boolean interrupted = false;
                
                Date nnow = new Date();
              
                while(to.getTime() - nnow.getTime() > 0) {
                    try{
                        // do stuff
                    	nnow = new Date();
                    	//Thread.currentThread().interrupt();
                    	Thread.sleep(2000);
                    }catch(InterruptedException e){
                    	System.out.println(Thread.currentThread().getName() +" Thread가 중지되었습니다.");
                    	interrupted = true;
                        break;
                    }
                }

                // end
                if(interrupted) {
					Map<String, String> jsonObjectExit = new HashMap<String, String>();
					jsonObjectExit.put("id", id);
					jsonObjectExit.put("powerStatus", "ON");
					return jsonObjectExit;
                }
                else {
	                System.out.println("-----------------------------------------------------------------------------------------------");
					System.out.println("PC 전원을 끕니다. [종료시각 : " + transFormat.format(new Date())+"]");
					Map<String, String> jsonObjectExit = new HashMap<String, String>();
					jsonObjectExit.put("id", id);
					jsonObjectExit.put("endTime", endTime);
					jsonObjectExit.put("powerStatus", "OFF");
					lc.getConnection();
					lc.getConnectionHset(id, jsonObjectExit);
					lc.getConnectionExit();
	
	                return jsonObjectExit;
                }
            }
        };
	}
}