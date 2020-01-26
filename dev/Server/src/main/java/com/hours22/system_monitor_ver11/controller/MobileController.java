package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hours22.system_monitor_ver11.db.DataService;
import com.hours22.system_monitor_ver11.db.LettuceController;
import com.hours22.system_monitor_ver11.vo.PcData;

@Controller
public class MobileController {
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
}