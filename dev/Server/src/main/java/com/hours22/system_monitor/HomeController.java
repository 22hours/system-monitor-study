package com.hours22.system_monitor;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static PC[] pc = new PC[100];
	private static int pcCnt = 0;
	private static JsonUtils JManager = new JsonUtils()
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHome(Locale locale, Model model) {
		logger.info("Welcome 22Hours! Home.jsp By GET");
		
		model.addAttribute("test", "by jjongwuner" );
		
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postHome(Locale locale, Model model) {
		logger.info("Welcome 22Hours! Home.jsp By POST");

		//pc[pcCnt] = new PC(pcCnt);
		//pcCnt++;
		
		model.addAttribute("test", "by jjongwuner" );
		
		return "home";
	}
	
	// JSON Get
	@RequestMapping(value = "/pc/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Map pcJsonGet(HttpServletRequest request, @PathVariable Long id){
		int idx = Integer.valueOf(id.toString());
		
		logger.info("Call GET Method - ["+id+"] pcJson ");
		
		
		/*
		result.put("id", Integer.toString(pc[idx].get_id()));
		result.put("name", pc[idx].get_name());
		result.put("power_status", (pc[idx].get_power_status() == true) ? "ON" : "OFF");
		result.put("cpu_data", Integer.toString(pc[idx].get_cpu_data()));
		result.put("ram_data", Integer.toString(pc[idx].get_ram_data()));
		result.put("start_time", Integer.toString(pc[idx].get_ram_data()));
		result.put("end_time", Integer.toString(pc[idx].get_ram_data()));
		*/

		//GET == 1) Local Load
		PC tpc = JManager.LocalLoad_JsonToObj(idx);
		
		// Get == 2) Http Response
		Map result = JManager.HttpResponse_ObjToJson(tpc); 

		
		return result;
	}
	// JSON Post

	@RequestMapping(value="/pc/{id}", method=RequestMethod.POST)
	@ResponseBody
	public void pcJsonPost(@RequestBody Map<String, String> tpc, @PathVariable Long id) {
		int idx = Integer.valueOf(id.toString());
		logger.info("Call POST Method - ["+id+"] pcJson ");
		logger.info(tpc.toString());
		
		// Post == 1) HTTP Request
		PC tpc = JManager.HttpRequest_JsonToObj(idx);
		
		// Post == 2) Local Save
		Map result = JManager.LocalSave_ObjToJson(tpc);
	}
}
