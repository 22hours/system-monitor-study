package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.client.ClientInfoController;
import com.hours22.system_monitor_ver11.db.DataService;
import com.hours22.system_monitor_ver11.db.LettuceController;
import com.hours22.system_monitor_ver11.vo.PcData;

@Controller
@WebServlet(asyncSupported = true)
public class MobileController extends HttpServlet{
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	LettuceController lc;
	
	@Autowired
	DataService dss;
	
	@Autowired
	ClientInfoController cic;
	
	Timer OffTimer, MsgTimer;
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@RequestMapping(value = "/mobile/pc", method = RequestMethod.GET)
	public void GetPcData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Input : /mobile/pc <- GET method [Client Ip : "+ cic.getClientIp(request)+"] at "+transFormat.format(new Date()) );
		
		lc.getConnection();
		
		String json = ojm.writeValueAsString(dss.GetAllPcDataRedis());
		json = dss.PrettyPrinter(json);
		System.out.println(json);
		
		lc.getConnectionExit();
		response.getWriter().print(json);
	}
	
	@RequestMapping(value = "/mobile/pc/{id}/data", method = RequestMethod.GET)
	public void GetPcRamCpuData(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		
		lc.getConnection();
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Input : /mobile/pc/"+id+"/data <- GET method [Client Ip : "+ cic.getClientIp(request) +" ] at " + transFormat.format(new Date()));
		
		String json = ojm.writeValueAsString(dss.GetAllPcDataRedis());
		json = dss.PrettyPrinter(json);
		System.out.println(json);
		
		response.getWriter().print(json);
		lc.getConnectionExit();
	}
	
	@RequestMapping(value = "/mobile/pc/{id}/power/{endTime}", method = RequestMethod.POST)
	public void PostPcPowerOff(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String endTime) throws IOException, InterruptedException, ParseException {
		
		response.setCharacterEncoding("UTF-8");
		
		final AsyncContext asyncContext = request.startAsync(request, response);
		asyncContext.setTimeout(900000000);
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Input : /mobile/pc/"+id+"/power/"+endTime+" <- POST method [Client Ip : "+cic.getClientIp(request) +" ] at "+transFormat.format(new Date()));
		
		if(OffTimer != null) {
			
			System.out.println("기존 종료시간이 변경되었습니다!");
			OffTimer.cancel();
			OffTimer = null;
		}
		OffTimer = new Timer();
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				Date nowTime = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				System.out.println("---------------------------------------------------------------------");
				System.out.println("PC 전원을 끕니다. [종료시각 : " + transFormat.format(nowTime)+"]");
				OffTimer = null;
				
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
		jsonObject.put("powerStatus", "ON");
		jsonObject.put("endTime", endTime);
		String jsonString = ojm.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
		
		//lc.getConnectionHsetAllData(id, jsonObject);
		lc.getConnectionHset(id,  jsonObject);
		// response
		response.getWriter().print(jsonString);
		System.out.println("hget 디버깅 결과 : " + jsonString);
		
		Date to = transFormat.parse(form);
		OffTimer.schedule(task, to);
		
		lc.getConnectionExit();
	}
}