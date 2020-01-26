package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	@RequestMapping(value = "/mobile/pc", method = RequestMethod.GET)
	public void GetPcData(HttpServletResponse response) throws IOException {
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		System.out.println("Input : /mobile/pc <- GET method ");
		//String json = ojm.writeValueAsString(dss.GetAllPcDataRedis());
		
		String json = ojm.writerWithDefaultPrettyPrinter().writeValueAsString(dss.GetAllPcDataRedis());

		json = json.replaceAll("\\\\r\\\\n", "");
		json = json.replaceAll("\\\\", "");
		System.out.println(json);
		response.getWriter().print(json);
	}
	
	@RequestMapping(value = "/mobile/pc/{id}", method = RequestMethod.GET)
	public void GetPcRamCpuData(HttpServletResponse response, @PathVariable String id) throws IOException {
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		System.out.println("Input : /mobile/pc/"+id+" <- GET method ");
		//String json = ojm.writeValueAsString(dss.GetAllPcDataRedis());
		
		String json = ojm.writerWithDefaultPrettyPrinter().writeValueAsString(lc.getConnectionHget(id));

		json = json.replaceAll("\\\\r\\\\n", "");
		json = json.replaceAll("\\\\", "");
		System.out.println(json);
		response.getWriter().print(json);
	}
	
	@RequestMapping(value = "/mobile/pc/{id}/power/{endTime}", method = RequestMethod.POST)
	public void PostPcPowerOff(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @PathVariable String endTime) throws IOException, InterruptedException, ParseException {
		final AsyncContext asyncContext = request.startAsync(request, response);

		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				JSONObject jsonObject = new JSONObject();
				
				System.out.println("PC 전원을 끕니다.");
				jsonObject.put("id", id);
				jsonObject.put("powerStatus", "OFF");
				System.out.println("response : " + jsonObject.toString());
				
				try {
					response.getWriter().print(jsonObject.toString());
					asyncContext.complete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		String form = endTime;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String [] seq = form.split("-");
		form = seq[0] + "-" + seq[1] + "-" + seq[2] +" "+seq[3] +":"+seq[4];
		System.out.println("종료예약 설정 [시간 : "+form+"]");
		
		Date to = transFormat.parse(form);
		timer.schedule(task, to);
		
		System.out.println("Input : /mobile/pc/"+id+"/power <- POST method ");
	}
}