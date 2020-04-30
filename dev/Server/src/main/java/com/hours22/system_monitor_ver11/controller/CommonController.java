package com.hours22.system_monitor_ver11.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

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
	
	@Autowired
	CacheController cache;
	
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	@CrossOrigin("*")
	@Async(value = "threadPoolHome")
	@RequestMapping(value = "/")
	public CompletableFuture<String> GetIndex(WebRequest req, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (req.checkNotModified(cache.GetCache())) {
			return null;
		}
		String msg = "Welcome 22Hours!<br>" + "This is system-monitor project. Version 3.0<br>" + "서버작업중 by jongchu. 2020-04-08<br><br><br><br>";

		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : / <- GET method [Client Ip : "+ cic.getClientIp(request) +" ] at " + transFormat.format(new Date()));
		return CompletableFuture.completedFuture("index.html");
	}
	
	@RequestMapping(value = "/testpage", method = RequestMethod.GET)
	public @ResponseBody String GetTestPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		dss.test();
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /testpage <- GET method [Client Ip : " +cic.getClientIp(request)+" ] at " + transFormat.format(new Date()));
		return "Test Page";
	}
    
	
	@RequestMapping(value = "/pc/{id}", method = RequestMethod.GET)
	public CompletableFuture<ResponseEntity<String>> GetPcInstanceData(HttpServletRequest req, HttpServletResponse response,  @PathVariable String id) throws IOException {
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : /pc/"+ id +" <- GET method [Client Ip : "+ cic.getClientIp(req) + " ] at " + transFormat.format(new Date()));
		
		//lc.getConnection();
		String res = lc.getConnectionHgetall(id);
		//lc.getConnectionExit();
		
		return CompletableFuture.completedFuture(ResponseEntity.ok().body(res));
	}
	
	@RequestMapping(value = "/pc/{id}", method = RequestMethod.POST)
	public void PostPcInstanceData(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map, @PathVariable String id) throws IOException {
		String json = ojm.writeValueAsString(map);
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("Input : "+ json +" <- POST method [Client : "+ cic.getClientIp(request) + " ] at "+transFormat.format(new Date()));
		
		
		//lc.getConnection();
		lc.getConnectionHset(id, map);
		//lc.getConnectionExit();
		
		response.getWriter().print("Success /pc/"+id+" <- POST method !!");

	}
}

