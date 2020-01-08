package com.hours22.system_monitor.Controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hours22.system_monitor.DataService;

@Controller
public class CommonController {
	@Autowired
	DataService dss;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void GetIndex(HttpServletResponse response) throws IOException {
		response.getWriter().print("Welcome 22Hours!");
		System.out.println("Input : / <- GET method ");
	}
	
	@RequestMapping(value = "/testpage", method = RequestMethod.GET)
	public void GetTestPage(HttpServletResponse response) throws IOException {
		response.getWriter().print("Test Page");
		dss.test();
		System.out.println("Input : /testpage <- GET method ");
	}

	@RequestMapping(value = "/pc/{id}", method = RequestMethod.GET)
	public void GetPcInstanceData(HttpServletResponse response,  @PathVariable Long id) throws IOException {
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		System.out.println("Input : /pc/"+ id +" <- GET method ");
	}
	
	@RequestMapping(value = "/pc/{id}", method = RequestMethod.POST)
	public void PostPcInstanceData(HttpServletRequest request,  Map<String, String> map) throws IOException {
		// HttpRequest_JsonToObj();
		// RedisSave_ObjToJson();
		System.out.println("Input : "+ map +" <- POST method ");
	}
}

