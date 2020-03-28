package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.client.ClientInfoController;
import com.hours22.system_monitor_ver11.client.ServerTimer;
import com.hours22.system_monitor_ver11.db.DataService;
import com.hours22.system_monitor_ver11.db.LettuceController;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
@WebServlet(asyncSupported = true)
public class PcController {
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	DataService dss;

	@Autowired
	LettuceController lc;
	
	@Autowired
	ClientInfoController cic;
	
	Date now = new Date();
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@RequestMapping(value = "/pc/{id}/data", method = RequestMethod.POST)
	public void PostPcData(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map, @PathVariable String id) throws IOException {
		response.setCharacterEncoding("UTF-8");

		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /pc/"+id+"/data <- POST method [Client Ip : " +cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
		lc.getConnection();
		lc.getConnectionHset(id, map);
		lc.getConnectionExit();
		System.out.println(map.toString());
		
    	Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
    	for(Thread thread : setOfThread){
    		System.out.println("Active Thread's [ Number : " +thread.getId()+" / Name : "+thread.getName()+" ] ");
    	    
    		String res = thread.getName();
    		if(res.equals("Mobile-GetNewPc")) {
    			thread.setName("Mobile-GetNewPc-"+id);
    			thread.interrupt();
    			System.out.println("******"+res+" 스레드를 종료시킵니다.******");
    		}
    	}
		
		response.getWriter().print("Success /pc/"+id+"/data <- POST method !!");
	}
	
	
	@RequestMapping(value = "/pc/{id}/power/{endTime}", method = RequestMethod.POST)
	public @ResponseBody Callable<Map<String, String>> PostPcPowerOff(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String endTime) throws IOException, InterruptedException, ParseException {
		
		response.setContentType("application/json;charset=UTF-8"); 

		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /pc/"+id+"/power/"+endTime+" <- POST method(언제꺼? or 연장신청 v2.0) [Client Ip : " +cic.getClientIp(request)+" ] at "+transFormat.format(new Date()));

		//-------------------------------------------------------------------
		
		lc.getConnection();
		String jsonStringForAndroid = lc.getConnectionHgetall(id); 
		System.out.println(jsonStringForAndroid);
		
		Date now = new Date();
		
		String eform = endTime;
		String startTime = transFormat.format(now);

		char[] sfromArr = null;
		sfromArr = startTime.toCharArray();
		for(int i=4; i<=13; i+=3) {
			sfromArr[i] = '-';
		}
		startTime = String.valueOf(sfromArr);
		
		//2020-05-15 11:34
		
		String [] eseq = eform.split("-");
		eform = eseq[0] + "-" + eseq[1] + "-" + eseq[2] +" "+eseq[3] +":"+eseq[4];
		System.out.println("종료예약 설정 [시간 : "+eform+"]");
		
		Map<String, String> jsonObject = new HashMap<String, String>();
		jsonObject.put("id", id);
		jsonObject.put("powerStatus", "ON");
		jsonObject.put("startTime", startTime);
		jsonObject.put("endTime", endTime);
		String jsonString = ojm.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
		
		//lc.getConnectionHsetAllData(id, jsonObject);
		lc.getConnectionHset(id,  jsonObject);
		
		// response
		System.out.println("hget 디버깅 결과 : " + jsonString);
		
		Date to = transFormat.parse(eform);
		lc.getConnectionExit();
		
		//-------------------------------------------------------------------
		
		int opt = 1;
		
    	Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
    	for(Thread thread : setOfThread){
    		System.out.println("Active Thread's [ Number : " +thread.getId()+" / Name : "+thread.getName()+" ] ");
    	    
    		String res = thread.getName();
    		if(res.equals("PCPowerOffExc-Return-"+id)) {
    			thread.interrupt();
    			System.out.println("******"+res+" 스레드를 종료시킵니다. [PowerReturn]******");
    			opt = 2;
    		};
    		if(res.equals("PCPowerOffMsg-Return-"+id)) {
    			thread.interrupt();
    			System.out.println("******"+res+" 스레드를 종료시킵니다. [MessageReturn]******");
    		}
    	}
		
		
		Thread ct = Thread.currentThread();
		ct.setName("PCPowerOffExc-"+id);
		System.out.println("현재 Thread [ ID : " + ct.getId()+ " / Name : "+ct.getName()+" ] ");   
		
		Thread.sleep(3000);
		//// DB 업데이트
		
		lc.getConnection();

		if(opt == 1) 
			System.out.println(id+" PC가 켜졌습니다! (변경된 methd)" + transFormat.format(now));
		else if(opt == 2) 
			System.out.println(id+" PC의 종료시간이  변경되었습니다." + transFormat.format(now));
		
		if(opt == 1)
			jsonObject.put("startTime", startTime);
		jsonString = ojm.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
		
		lc.getConnectionHset(id,  jsonObject);
		
		// response
		System.out.println("hget 디버깅 결과 : " + jsonString);
		System.out.println("Connection 바로 앞.");
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
                    	System.out.println(Thread.currentThread().getName() +" Thread가 중지되었습니다.[Power]");
                    	interrupted = true;
                        break;
                    }
                }

                // end
                if(interrupted) {
                	System.out.println("###### 일단 인터럽트 되긴 했는데..... ######");
            		char[] nowArr = null;
            		String nowTime = transFormat.format(new Date());
            		lc.getConnection();
            		nowArr = nowTime.toCharArray();
            		for(int i=4; i<=13; i+=3) {
            			nowArr[i] = '-';
            		}
            		nowTime = String.valueOf(nowArr);
            		
					Map<String, String> jsonObjectExit = new HashMap<String, String>();
					jsonObjectExit.put("id", id);
					String tmp = lc.getConnectionHgetField(id, "endTime");
					if(nowTime.compareTo(tmp)>=0) {
                		jsonObjectExit.put("powerStatus", "OFF");
                		jsonObjectExit.put("endTime", tmp);
                		System.out.println("즉시 종료합니다. [명령]");
                		System.out.println("["+tmp + " vs " + nowTime+"]");
                		// nowTime >= tmp 이면 끈다.
					}
                	else { 
                		jsonObjectExit.put("powerStatus", "ON");
                		jsonObjectExit.put("endTime", tmp);
                		System.out.println("연장신청 [명령]");
                		System.out.println("["+tmp + " vs " + nowTime+"]");
                	}
                	lc.getConnectionExit();
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

	@RequestMapping(value = "/pc/{id}/message/{min}", method = RequestMethod.GET)
	public @ResponseBody Callable<Map<String, String>> GetWarningMsg(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String min) throws IOException, InterruptedException, ParseException {
		response.setContentType("application/json;charset=UTF-8"); 
		
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /pc/"+id+"/msg/"+min+" <- GET method [Client Ip : "+ cic.getClientIp(request)+" ] at "+transFormat.format(new Date()));
		
		
    	Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
    	for(Thread thread : setOfThread){
    		System.out.println("Active Thread's [ Number : " +thread.getId()+" / Name : "+thread.getName()+" ] ");
    	    
    		String res = thread.getName();
    		if(res.equals("PCPowerOffMsg-Return-"+id)) {
    			thread.interrupt();
    			System.out.println("******"+res+" 스레드를 종료시킵니다.******");
    		}
    	}
		
		
		
		Thread ct = Thread.currentThread();
		ct.setName("PCPowerOffMsg-"+id);
		System.out.println("현재 Thread [ ID : " + ct.getId()+" / Name : "+ct.getName()+" ] ");  
		System.out.println("[system-monitor 알림봇 메세지 !"+min+"분전 알람!");
		

		
		String EndTime;
		lc.getConnection();
		EndTime = lc.getConnectionHgetField(id, "endTime");
		lc.getConnectionExit();
		
		System.out.println("일단 redis에서 불러온 값 : "+ EndTime);
		
		String form = EndTime;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String [] seq = form.split("-");
		form = seq[0] + "-" + seq[1] + "-" + seq[2] +" "+seq[3] +":"+seq[4];
		Date to = transFormat.parse(form);
		Date now = new Date();
		
		int beforeMin = Integer.parseInt(min);
		Calendar cal = Calendar.getInstance();
		cal.setTime(to);
		cal.add(Calendar.MINUTE, -beforeMin);
		
		Date goal = cal.getTime();
		long endTime = goal.getTime() - now.getTime();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "PC 종료 "+min+"분전입니다! 연장신청을 하거나, 자료정리를 서둘러주세요!");
		jsonObject.put("id", id);
		//return new AsyncResult<Map<String, String>>(jsonObject);
		
		return new Callable<Map<String, String>>() {
            @Override
            public Map<String, String> call() throws Exception {
            	System.out.println("================================");
            	System.out.println("PCPowerOffMsg Return 영역");
        		Thread.currentThread().setName("PCPowerOffMsg-Return-"+id);
            	System.out.println("Thread ID : " + Thread.currentThread().getId());
            	System.out.println("Thread Name : " + Thread.currentThread().getName());
            	
            	long MSG = endTime;
            	System.out.println(MSG / 1000 + "초 동안 Thread.sleep();");
            	System.out.println("알림시간 is " + EndTime +" - "+min+"분");
                //Thread.sleep(252000);
                // start
                
                long timeToSleep = MSG;
                long start, end, slept;
                boolean interrupted = false;
                
                Date now = new Date();
              
                while(goal.getTime() - now.getTime() > 0) {
                    try{
                        // do stuff
                    	now = new Date();
                    	//Thread.currentThread().interrupt();
                    	Thread.sleep(2000);
                    }catch(InterruptedException e){
                    	System.out.println(Thread.currentThread().getName() +" Thread가 중지되었습니다. [Msg]");
                    	interrupted = true;
                        break;
                    }
                }

                // end
                if(interrupted) {
					Map<String, String> jsonObjectExit = new HashMap<String, String>();
					jsonObjectExit.put("id", id);
					jsonObjectExit.put("msg", "extension");
					return jsonObjectExit;
                }
                else {
                // end
					JSONObject jsonObject = new JSONObject();
	
					System.out.println("--------------------------------------------------------------------------------------------");
					System.out.println("[종료"+ min +"분전 알림 메세지! PC : "+ id +" ]  at " + transFormat.format(new Date()));
					jsonObject.put("id", id);
					jsonObject.put("msg", "PC 종료 "+min+"분전입니다! 연장신청을 하거나, 자료정리를 서둘러주세요!");
					
					System.out.println("response : " + jsonObject.toString());
	                return jsonObject;
                }
            }
        };
	}
}