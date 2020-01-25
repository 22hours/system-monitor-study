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
import com.hours22.system_monitor_ver11.vo.PcData;

@Controller
public class MobileController {
	@Autowired
	ObjectMapper ojm;
	
	@Autowired
	DataService dss;
	
	@RequestMapping(value = "/phone", method = RequestMethod.GET)
	public void GetTotalPcData(HttpServletResponse response) throws IOException {
		System.out.println("Input : /pc All Data <- GET method ");
		System.out.println("Input : <- Total GET method ");
	}
}