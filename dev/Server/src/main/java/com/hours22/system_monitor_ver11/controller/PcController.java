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

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.client.ClientInfoController;
import com.hours22.system_monitor_ver11.db.DataService;
import com.hours22.system_monitor_ver11.db.LettuceController;

@Controller
public class PcController {
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	DataService dss;

	@Autowired
	LettuceController lc;
	
	@Autowired
	ClientInfoController cic;
	
	Timer timer;
	Date now = new Date();
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@RequestMapping(value = "/pc/{id}/data", method = RequestMethod.POST)
	public void PostPcData(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map, @PathVariable String id) throws IOException {
		response.setCharacterEncoding("UTF-8");
		
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		System.out.println("Input : /pc/"+id+"/data <- POST method [Client Ip : " +cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
		//String json = ojm.writeValueAsString(dss.GetAllPcDataRedis());
		lc.getConnection();
		lc.getConnectionHset(id, map);
		lc.getConnectionExit();
		System.out.println(map.toString());
		
		response.getWriter().print("Success /pc/"+id+"/data <- POST method !!");
	}
	
	@RequestMapping(value = "/pc/{id}/power/{endTime}", method = RequestMethod.POST)
	public void PostPcPowerOff(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String endTime) throws IOException, InterruptedException, ParseException {
		response.setCharacterEncoding("UTF-8");
		
		final AsyncContext asyncContext = request.startAsync(request, response);
		asyncContext.setTimeout(900000000);
		
		if(timer != null) {
			System.out.println("기존 종료시간이 변경되었습니다!");
			timer.cancel();
			timer = null;
		}
		timer = new Timer();
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				Date nowTime = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				System.out.println("PC 전원을 끕니다. [종료시각 : " + transFormat.format(nowTime)+"]");
				timer = null;
				
				Map<String, String> jsonObjectExit = new HashMap<String, String>();
				jsonObjectExit.put("id", id);
				jsonObjectExit.put("powerStatus", "OFF");
				lc.getConnection();
				lc.getConnectionHset(id, jsonObjectExit);
				lc.getConnectionExit();
				
				asyncContext.complete();
			}
		};
		
		lc.getConnection();
		String jsonStringForAndroid = lc.getConnectionHgetall(id); 
		System.out.println(jsonStringForAndroid);
		// to send android!
		// 어플이 먼저켜지고나서, 어플에서 long polling으로 업데이트.

		
		Date now = new Date();
		String form = endTime;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println("PC가 켜졌습니다!" + transFormat.format(now));
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
		response.getWriter().print(jsonString);
		System.out.println("hget 디버깅 결과 : " + jsonString);
		
		Date to = transFormat.parse(form);
		timer.schedule(task, to);
		
		System.out.println("Input : /pc/"+id+"/power/"+endTime+" <- POST method(언제꺼?) [Client Ip : " +cic.getClientIp(request)+" ] at "+transFormat.format(new Date()));
		lc.getConnectionExit();
	}

	@RequestMapping(value = "/pc/{id}/message/{min}", method = RequestMethod.GET)
	public void GetWarningMsg(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String min) throws IOException, InterruptedException, ParseException {
		final AsyncContext asyncContext = request.startAsync(request, response);
		response.setContentType("application/json;charset=UTF-8"); 
		asyncContext.setTimeout(900000000);
		
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
		timer = new Timer();
		
		
		TimerTask warningTask = new TimerTask() {
			@Override
			public void run() {
				
				JSONObject jsonObject = new JSONObject();
				
				System.out.println("[종료"+ min +"분전 알림 메세지!]");
				jsonObject.put("id", id);
				if(min == "2") {
					jsonObject.put("msg", "잠시 후 PC가 종료될 예정입니다. 오늘 하루도 고생많으셨습니다. ");
				}
				else {
					jsonObject.put("msg", "PC 종료 "+min+"분전입니다! 연장신청을 하거나, 자료정리를 서둘러주세요!");
				}
				System.out.println("response : " + jsonObject.toString());
				try {
					response.getWriter().print(jsonObject.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				asyncContext.complete();
			}
		};
		
		System.out.println("[system-monitor 알림봇 메세지 !"+min+"분전 알람!");
		
		String EndTime;
		lc.getConnection();
		EndTime = lc.getConnectionHgetField(id, "endTime");
		lc.getConnectionExit();
		
		String form = EndTime;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String [] seq = form.split("-");
		form = seq[0] + "-" + seq[1] + "-" + seq[2] +" "+seq[3] +":"+seq[4];
		Date to = transFormat.parse(form);
		
		int beforeMin = Integer.parseInt(min);
		Calendar cal = Calendar.getInstance();
		cal.setTime(to);
		cal.add(Calendar.MINUTE, -beforeMin);
		
		timer.schedule(warningTask, cal.getTime());
		
		System.out.println("Input : /pc/"+id+"/msg/"+min+" <- GET method [Client Ip : "+ cic.getClientIp(request)+" ] at "+transFormat.format(new Date()));
	}
}