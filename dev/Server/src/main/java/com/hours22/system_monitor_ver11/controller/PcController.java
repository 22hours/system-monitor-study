package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
		response.getWriter().print("Success /pc/"+id+"/data <- POST method !!");
	}
	
	
	@RequestMapping(value = "/pc/{id}/power/{endTime}", method = RequestMethod.POST)
	public @ResponseBody Callable<Map<String, String>> PostPcPowerOff(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String endTime) throws IOException, InterruptedException, ParseException {
		
		response.setContentType("application/json;charset=UTF-8"); 

		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /pc/"+id+"/power/"+endTime+" <- POST method(언제꺼? v2.0) [Client Ip : " +cic.getClientIp(request)+" ] at "+transFormat.format(new Date()));
		
		
		lc.getConnection();
		String jsonStringForAndroid = lc.getConnectionHgetall(id); 
		System.out.println(jsonStringForAndroid);
		// to send android!
		// 어플이 먼저켜지고나서, 어플에서 long polling으로 업데이트.

		
		Date now = new Date();
		String form = endTime;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println("PC가 켜졌습니다! (변경된 methd)" + transFormat.format(now));
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
            	
            	
            	long MSG = ServerTimer.GetTime(endTime);
            	System.out.println(MSG / 1000 + "초 동안 Thread.sleep();");
            	System.out.println("Endtime is " + endTime);
                //Thread.sleep(252000);
                // start
                
                long timeToSleep = MSG;
                long start, end, slept;
                boolean interrupted = false;

                while(timeToSleep > 0){
                    start=System.currentTimeMillis();
                    try{
                        Thread.sleep(timeToSleep);
                        break;
                    }
                    catch(InterruptedException e){
                        //work out how much more time to sleep for
                        end=System.currentTimeMillis();
                        slept=end-start;
                        timeToSleep-=slept;
                        interrupted=true;
                        System.out.println("Thread Interrupt ! at " + transFormat.format(new Date()));
                    }
                }
                if(interrupted){
                    //restore interruption before exit
                    Thread.currentThread().interrupt();
                }
                
                // end
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
        };
	}

	@RequestMapping(value = "/pc/{id}/message/{min}", method = RequestMethod.GET)
	public @ResponseBody Callable<Map<String, String>> GetWarningMsg(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String min) throws IOException, InterruptedException, ParseException {
		response.setContentType("application/json;charset=UTF-8"); 
		
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /pc/"+id+"/msg/"+min+" <- GET method [Client Ip : "+ cic.getClientIp(request)+" ] at "+transFormat.format(new Date()));
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
		
		long endTime = cal.getTime().getTime() - now.getTime();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "PC 종료 "+min+"분전입니다! 연장신청을 하거나, 자료정리를 서둘러주세요!");
		jsonObject.put("id", id);
		//return new AsyncResult<Map<String, String>>(jsonObject);
		
		return new Callable<Map<String, String>>() {
            @Override
            public Map<String, String> call() throws Exception {
            	
            	
            	long MSG = endTime;
            	System.out.println(MSG / 1000 + "초 동안 Thread.sleep();");
            	System.out.println("알림시간 is " + EndTime +" - "+min+"분");
                //Thread.sleep(252000);
                // start
                
                long timeToSleep = MSG;
                long start, end, slept;
                boolean interrupted = false;

                while(timeToSleep > 0){
                    start=System.currentTimeMillis();
                    try{
                        Thread.sleep(timeToSleep);
                        break;
                    }
                    catch(InterruptedException e){
                        //work out how much more time to sleep for
                        end=System.currentTimeMillis();
                        slept=end-start;
                        timeToSleep-=slept;
                        interrupted=true;
                        System.out.println("Thread Interrupt ! at " + transFormat.format(new Date()));
                    }
                }
                if(interrupted){
                    //restore interruption before exit
                    Thread.currentThread().interrupt();
                }
                
                // end
				JSONObject jsonObject = new JSONObject();

				System.out.println("--------------------------------------------------------------------------------------------");
				System.out.println("[종료"+ min +"분전 알림 메세지!]");
				jsonObject.put("id", id);
				jsonObject.put("msg", "PC 종료 "+min+"분전입니다! 연장신청을 하거나, 자료정리를 서둘러주세요!");
				
				System.out.println("response : " + jsonObject.toString());
                return jsonObject;
            }
        };
	}
}