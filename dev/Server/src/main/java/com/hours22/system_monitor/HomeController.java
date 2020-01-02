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
	private static PC[] pc = new PC[100];
	private static int pcCnt = 0;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHome(Locale locale, Model model) {
		logger.info("Welcome 22Hours! Home.jsp By GET");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("test", "by jjongwuner" );
		
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postHome(Locale locale, Model model) {
		logger.info("Welcome 22Hours! Home.jsp By POST");
		logger.info("Make a PC Instance! ID : "+pcCnt);
		

		pc[pcCnt] = new PC(pcCnt);
		model.addAttribute("test", "by jjongwuner" );
		
		pcCnt++;
		return "home";
	}
	
	
	@RequestMapping(value = "/pc/{id}", method = RequestMethod.GET)
	public String pc_getOverallData(Model model, @PathVariable Long id) {
		logger.info("Pc Instances 접속! ID : "+ id +" By GET");
		logger.info("정보를 출력합니다.");
		// 모든 PC의 instance를 출력하는 형태
		//model.addAttribute("name", "jjongwuner );
		//model.addAttribute("cpudata", "12.11%" );
		//model.addAttribute("ramdata", "8.34%" );
		//model.addAttribute("power_status", "on" );
		int idx = Integer.valueOf(id.toString());
		
		model.addAttribute("name", pc[idx].get_name() );
		model.addAttribute("cpudata", pc[idx].get_cpu_data()+"%" );
		model.addAttribute("ramdata", pc[idx].get_ram_data()+"%" );
		model.addAttribute("power_status", (pc[idx].get_power_status()) ? "ON" : "OFF" );
				
		return "pc_overall";
	}
	
	
	@RequestMapping(value = "/pc/{id}", method = RequestMethod.POST)
	public void pc_postOverallData(Model model, @PathVariable Long id) {
		logger.info("Update aPC Instance! ID : "+ id +" By POST");
		int idx = Integer.valueOf(id.toString());
		
		pc[idx].set_name("jjongwunerPC");
		pc[idx].set_power_status(true);
		pc[idx].set_ram_data(15);
		pc[idx].set_cpu_data(52);
	}
	
	@RequestMapping(value = "/pc/{id}/name/{tname}", method = RequestMethod.POST)
	public void pc_postOverallData(Model model, @PathVariable Long id, @PathVariable String tname) {
		logger.info("Update aPC Instance! ID : [Name] "+ id +" By POST");
		int idx = Integer.valueOf(id.toString());
		
		pc[idx].set_name(tname);
	}
	
	@RequestMapping(value = "/pc/{id}/power/{opt}", method = RequestMethod.POST)
	public void pc_postPowerStatus(Model model, @PathVariable Long id, @PathVariable Long opt) {
		logger.info("Update aPC Instance! ID : [Power On/Off] "+ id +" By POST");
		int idx = Integer.valueOf(id.toString());
		int tmp = Integer.valueOf(opt.toString());
		boolean button = tmp !=0 ? true : false;
		
		pc[idx].set_power_status(button);
	}
	
	@RequestMapping(value = "/pc/{id}/cpu/{val}", method = RequestMethod.POST)
	public void pc_postCpuData(Model model, @PathVariable Long id, @PathVariable Long val) {
		logger.info("Update aPC Instance! ID : [Cpu] "+ id +" By POST");
		int idx = Integer.valueOf(id.toString());
		int value = Integer.valueOf(val.toString());
		pc[idx].set_cpu_data(value);
	}
	
	@RequestMapping(value = "/pc/{id}/ram/{val}", method = RequestMethod.POST)
	public void pc_postRamData(Model model, @PathVariable Long id, @PathVariable Long val) {
		logger.info("Update aPC Instance! ID : [Ram] "+ id +" By POST");
		int idx = Integer.valueOf(id.toString());
		int value = Integer.valueOf(val.toString());
		pc[idx].set_ram_data(value);
	}
	
	
	
	@RequestMapping(value = "/pc/{id}/power", method = RequestMethod.GET)
	public String pc_getPowerData(Model model, @PathVariable Long id) {
		logger.info("Pc Instances 접속! By GET");
		// 모든 PC의 instance를 출력하는 형태
		model.addAttribute("name", "junghwan" );
		model.addAttribute("power_status", "on" );
		return "pc_power";
	}
	
	@RequestMapping(value = "/pc/{id}/cpudata", method = RequestMethod.GET)
	public String pc_getCpuData(Model model, @PathVariable Long id) {
		logger.info("Pc Instances 접속! By GET");
		// 모든 PC의 instance를 출력하는 형태
		model.addAttribute("name", "damin" );
		model.addAttribute("cpudata", "55.23%" );
		return "pc_cpudata";
	}
	
	@RequestMapping(value = "/pc/{id}/ramdata", method = RequestMethod.GET)
	public String pc_getRamData(Model model, @PathVariable Long id) {
		logger.info("Pc Instances 접속! By GET");
		// 모든 PC의 instance를 출력하는 형태
		model.addAttribute("name", "jungu" );
		model.addAttribute("ramdata", "22.41%" );
		return "pc_ramdata";
	}
}
