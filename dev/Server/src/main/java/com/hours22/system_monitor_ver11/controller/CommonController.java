package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.db.DataService;
import com.hours22.system_monitor_ver11.vo.PcData;

@Controller
public class CommonController {
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	DataService dss;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void GetIndex(HttpServletResponse response) throws IOException {
		response.getWriter().print("Welcome 22Hours!");
		System.out.println("Input : / <- GET method ");
	}
	
	@RequestMapping(value = "/testpage", method = RequestMethod.GET)
	public @ResponseBody String GetTestPage(HttpServletResponse response) throws IOException {
		dss.test();
		System.out.println("Input : /testpage <- GET method ");
		return "Test Page";
	}

	@RequestMapping(value = "/pc/{id}", method = RequestMethod.GET)
	public void GetPcInstanceData(HttpServletResponse response,  @PathVariable String id) throws IOException {
		System.out.println("Input : /pc/"+ id +" <- GET method ");
		
		// RedisLoad_JsonToObj();
		PcData pc = dss.RedisLoad_JsonToObj(id);
		
		// HttpResponse_ObjToJson();
		String json = ojm.writeValueAsString(pc);
		System.out.println(json);

		response.getWriter().print(json);
	}
	
	@RequestMapping(value = "/pc/{id}", method = RequestMethod.POST)
	public void PostPcInstanceData(HttpServletRequest request,  @RequestBody Map<String, String> map) throws IOException {
		String json = ojm.writeValueAsString(map);
		System.out.println("Input : "+ json +" <- POST method ");
		
		
		// HttpRequest_JsonToObj();
		PcData pc = ojm.readValue(json, PcData.class);
			//System.out.println(pc.get_name());
		dss.RedisSave_ObjToJson(pc);
	}
}

