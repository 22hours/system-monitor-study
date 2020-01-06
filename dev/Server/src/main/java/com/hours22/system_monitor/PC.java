package com.hours22.system_monitor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;


public class PC {
	private int id;
	private String name;
	private boolean power_status;
	private int cpu_data;
	private int ram_data;
	private Date start_time;
	private Date end_time;
	
	
	public PC(int tid) {
		this.id = tid;
		this.name = null;
		this.power_status = false;
		this.cpu_data = 0;
		this.ram_data = 0;
		
		Date td = new Date();
		String s = td.toString();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(td);
		cal.add(Calendar.MINUTE, 180);
		
		this.start_time = td;
		this.end_time = cal.getTime();
		
	}
	public boolean get_power_status() {return this.power_status;} 
	public int get_id() {return this.id;}
	public String get_name() {return this.name;}
	public int get_cpu_data() {return this.cpu_data;}
	public int get_ram_data() {return this.ram_data;}
	public Date get_start_time() {return this.start_time;}
	public Date get_end_time() {return this.end_time;}
	
	public void set_power_status(boolean opt) { this.power_status = (opt) ? true : false;}
	public void set_id(int tid) {this.id = tid;}
	public void set_name(String tname) {this.name = tname;}
	public void set_cpu_data(int cdata) {this.cpu_data = cdata;}
	public void set_ram_data(int rdata) {this.ram_data = rdata;}
	public void set_start_time(Date tstime) {this.start_time = tstime;}
	public void set_end_time(Date tetime) {this.end_time = tetime;}
	
	@Override
	public String toString() {
		
		String result = "";
		result = "[id:"+ id + ", name:" + name + ", power_status:"+ power_status + ", cpu_data:" + cpu_data + ", ram_data:" + ram_data + ", start_time:"+ start_time + ", end_time:"+ end_time + "]";
		return result;
	}
}
