package com.hours22.system_monitor;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome 22Hours! Home.jsp");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("test", "by jjongwuner" );
		
		return "home";
	}
	
	
	@RequestMapping(value = "/pc/{id}")
	public String pc_getOverallData(Model model, @PathVariable Long id) {
		logger.info("Pc Instances 접속!");
		// 모든 PC의 instance를 출력하는 형태
		model.addAttribute("name", "jjongwuner" );
		model.addAttribute("cpudata", "12.11%" );
		model.addAttribute("ramdata", "8.34%" );
		model.addAttribute("power_status", "on" );
		return "pc_overall";
	}
	
	@RequestMapping(value = "/pc/{id}/power")
	public String pc_getPowerData(Model model, @PathVariable Long id) {
		logger.info("Pc Instances 접속!");
		// 모든 PC의 instance를 출력하는 형태
		model.addAttribute("name", "junghwan" );
		model.addAttribute("power_status", "on" );
		return "pc_power";
	}
	
	@RequestMapping(value = "/pc/{id}/cpudata")
	public String pc_getCpuData(Model model, @PathVariable Long id) {
		logger.info("Pc Instances 접속!");
		// 모든 PC의 instance를 출력하는 형태
		model.addAttribute("name", "damin" );
		model.addAttribute("cpudata", "55.23%" );
		return "pc_cpudata";
	}
	
	@RequestMapping(value = "/pc/{id}/ramdata")
	public String pc_getRamData(Model model, @PathVariable Long id) {
		logger.info("Pc Instances 접속!");
		// 모든 PC의 instance를 출력하는 형태
		model.addAttribute("name", "jungu" );
		model.addAttribute("ramdata", "22.41%" );
		return "pc_ramdata";
	}
}
