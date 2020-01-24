package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hours22.system_monitor_ver11.db.DataService;

@Controller
public class PcController {
	@Autowired
	DataService dss;
	
	@RequestMapping(value = "/pc", method = RequestMethod.GET)
	public void GetPcData(HttpServletResponse response) throws IOException {
		// RedisLoad_JsonToObj();
		// HttpResponse_ObjToJson();
		System.out.println("Input : /pc <- GET method ");
	}

}