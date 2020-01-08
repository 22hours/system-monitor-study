package com.hours22.system_monitor.Controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hours22.system_monitor.DataService;

@Controller
public class MobileController {
	@Autowired
	DataService dss;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void GetTotalPcData(HttpServletResponse response) throws IOException {
		// 모든 PC의 JSON 데이터 GET
	}

}