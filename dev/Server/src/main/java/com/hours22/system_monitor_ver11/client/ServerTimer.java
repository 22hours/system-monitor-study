package com.hours22.system_monitor_ver11.client;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Controller;

@Controller
public class ServerTimer {
	public static long GetTime(String time) {
		// 2020-03-02-16-12
		Date now = new Date();
		Calendar calUntil = Calendar.getInstance();
	
		calUntil.set(Calendar.YEAR, Integer.parseInt(time.split("-")[0]));
		calUntil.set(Calendar.MONTH, Integer.parseInt(time.split("-")[1]) - 1);
		calUntil.set(Calendar.DAY_OF_WEEK, Integer.parseInt(time.split("-")[2]));
		calUntil.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split("-")[3]));
		calUntil.set(Calendar.MINUTE, Integer.parseInt(time.split("-")[4]));
		calUntil.set(Calendar.SECOND, 0);
		
		Date until = calUntil.getTime();
		long sleep = until.getTime() - now.getTime(); 
		return sleep;
	}
}
