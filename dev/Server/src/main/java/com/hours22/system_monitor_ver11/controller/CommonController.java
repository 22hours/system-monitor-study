package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.client.ClientInfoController;
import com.hours22.system_monitor_ver11.db.DataService;
import com.hours22.system_monitor_ver11.db.LettuceController;
import com.hours22.system_monitor_ver11.vo.PcData;

import io.lettuce.core.output.KeyValueStreamingChannel;

@Controller
public class CommonController {
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	DataService dss;
	
	@Autowired
	LettuceController lc;
	
	@Autowired
	ClientInfoController cic;
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void GetIndex(HttpServletRequest req, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().println("Welcome 22Hours!<br>");
		response.getWriter().println("This is system-monitor project.<br>");
		response.getWriter().println("서버작업중 by jongchu. 2020-03-01 15:30<br><br><br><br>");

		response.getWriter().println("O response : 값 전해주기.<br>" + 
				"△ 예외처리 test.<br>" + 
				"O request ip 추가하기<br>" + 
				"O nohup on/off.<br>" + 
				"O message 글씨 깨짐현상. (v1.2.7<br>" + 
				"O pc/연장신청, db에 반영이 안됌. (v1.2.7)<br>" + 
				"O url로만 받으면 될것 같은데, endtime 굳이 json으로 받을 필요 없지 않는가. ----> Client 규약 명확하게 하면 문제발생 X<br>" + 
				"O method에 현재시간 추가해주기.<br>" + 
				"O pc 언제꺼 ??? db에 반영 됌.<br>"  + 
				"- observer pattern 적용 필요\r\n" + 
				"- 비동기 처리 필요");
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : / <- GET method [Client Ip : "+ cic.getClientIp(req) +" ] at " + transFormat.format(new Date()));
	}
	
	@RequestMapping(value = "/testpage", method = RequestMethod.GET)
	public @ResponseBody String GetTestPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		dss.test();
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /testpage <- GET method [Client Ip : " +cic.getClientIp(request)+" ] at " + transFormat.format(new Date()));
		return "Test Page";
	}
    
	@RequestMapping(value = "/pc/{id}", method = RequestMethod.GET)
	public @ResponseBody String GetPcInstanceData(HttpServletRequest req, HttpServletResponse response,  @PathVariable String id) throws IOException {
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /pc/"+ id +" <- GET method [Client Ip : "+ cic.getClientIp(req) + " ] at " + transFormat.format(new Date()));
		
		lc.getConnection();
		String res = lc.getConnectionHgetall(id);
		lc.getConnectionExit();
		
		return res;
	}
	
	@RequestMapping(value = "/pc/{id}", method = RequestMethod.POST)
	public void PostPcInstanceData(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map, @PathVariable String id) throws IOException {
		String json = ojm.writeValueAsString(map);
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : "+ json +" <- POST method [Client : "+ cic.getClientIp(request) + " ] at "+transFormat.format(new Date()));
		
		
		lc.getConnection();
		lc.getConnectionHset(id, map);
		lc.getConnectionExit();
		
		response.getWriter().print("Success /pc/"+id+" <- POST method !!");

	}
}

