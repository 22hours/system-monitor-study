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
import org.springframework.util.ObjectUtils;
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
	
	
	@RequestMapping(value = "/pc/{id}/power/{endTime}", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> PostPcPower(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map, @PathVariable String id, @PathVariable String endTime) throws IOException, InterruptedException {
		response.setCharacterEncoding("UTF-8");

		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /pc/"+id+"/power/"+endTime+" <- POST method [Client Ip : " +cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
		
		boolean fg = false;
    	Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
    	for(Thread thread : setOfThread){
    		System.out.println("Active Thread's [ Number : " +thread.getId()+" / Name : "+thread.getName()+" ] ");
    	    
    		String res = thread.getName();
    		if(res.equals("PCPowerOffMsg-Return-"+id)) {
    			thread.interrupt();
    			System.out.println("******"+res+" 스레드를 종료시킵니다.******");
    			fg = true;
    		}
    	}
    	if(fg) Thread.sleep(3000);
    	
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
					lc.getConnection();
					String endTime = lc.getConnectionHgetField(id, "endTime");
					lc.getConnectionExit();
					
					jsonObjectExit.put("endTime", endTime);
					return jsonObjectExit;
                }
                else {
                // end
					JSONObject jsonObject = new JSONObject();
	
					System.out.println("--------------------------------------------------------------------------------------------");
					System.out.println("[종료"+ min +"분전 알림 메세지! PC : "+ id +" ]  at " + transFormat.format(new Date()));
					jsonObject.put("id", id);
					jsonObject.put("msg", min);
					
					lc.getConnection();
					String endTime = lc.getConnectionHgetField(id, "endTime");
					lc.getConnectionExit();
					
					jsonObject.put("endTime", endTime);
					
					System.out.println("response : " + jsonObject.toString());
	                return jsonObject;
                }
            }
        };
	}
}