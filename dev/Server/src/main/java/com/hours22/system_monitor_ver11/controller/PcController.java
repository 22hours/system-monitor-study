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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	Timer timer;
	String EndTime;
	
	@RequestMapping(value = "/pc/{id}/data", method = RequestMethod.POST)
	public void PostPcData(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map, @PathVariable String id) throws IOException {
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		System.out.println("Input : /mobile/pc <- POST method ");
		//String json = ojm.writeValueAsString(dss.GetAllPcDataRedis());
		lc.getConnection();
		lc.getConnectionHsetData(id, map);
		lc.getConnectionExit();
		System.out.println(map.toString());
	}
	
	
	@RequestMapping(value = "/pc/{id}/power/{endTime}", method = RequestMethod.POST)
	public void PostPcPowerOff(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String endTime) throws IOException, InterruptedException, ParseException {
		final AsyncContext asyncContext = request.startAsync(request, response);
		asyncContext.setTimeout(900000000);
		
		if(timer != null) {
			System.out.println("���� ����ð��� ����Ǿ����ϴ�!");
			timer.cancel();
			timer = null;
		}
		timer = new Timer();
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				Date nowTime = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				System.out.println("PC ������ ���ϴ�. [����ð� : " + transFormat.format(nowTime)+"]");
				timer = null;
				
				asyncContext.complete();
			}
		};
		
		lc.getConnection();
		String jsonStringForAndroid = lc.getConnectionHgetall(id); 
		System.out.println(jsonStringForAndroid);
		// to send android!
		// ������ ������������, ���ÿ��� long polling���� ������Ʈ.

		
		Date now = new Date();
		String form = EndTime = endTime;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println("PC�� �������ϴ�!" + transFormat.format(now));
		String [] seq = form.split("-");
		form = seq[0] + "-" + seq[1] + "-" + seq[2] +" "+seq[3] +":"+seq[4];
		System.out.println("���Ό�� ���� [�ð� : "+form+"]");
		
		Map<String, String> jsonObject = new HashMap<String, String>();
		jsonObject.put("id", id);
		jsonObject.put("powerStatus", "OFF");
		jsonObject.put("endTime", endTime);
		String jsonString = ojm.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
		response.getWriter().print(jsonString);
		
		Date to = transFormat.parse(form);
		timer.schedule(task, to);
		
		System.out.println("Input : /mobile/pc/"+id+"/power <- POST method ");
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
				
				System.out.println("PC ������ ���ϴ�.");
				jsonObject.put("id", id);
				if(min == "2") {
					jsonObject.put("msg", "��� �� PC�� ����� �����Դϴ�. ���� �Ϸ絵 ��������̽��ϴ�. ");
				}
				else {
					jsonObject.put("msg", "PC ���� "+min+"�����Դϴ�! �����û�� �ϰų�, �ڷ������� ���ѷ��ּ���!");
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
		
		System.out.println("[system-monitor �˸��� �޼��� !"+min+"�� �˶�!");
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
		
		System.out.println("Input : /mobile/pc/"+id+"/msg <- GET method ");
	}
}