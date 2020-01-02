package com.hours22.system_monitor;

import org.springframework.beans.factory.annotation.Autowired;


public class PC {
	private boolean power_status;
	private int id;
	private String name;
	private int cpu_data;
	private int ram_data;
	
	public PC(int tid) {
		this.id = tid;
		this.power_status = false;
		this.name = null;
		this.cpu_data = 0;
		this.ram_data = 0;
	}
	public boolean get_power_status() {return this.power_status;} 
	public int get_id() {return this.id;}
	public String get_name() {return this.name;}
	public int get_cpu_data() {return this.cpu_data;}
	public int get_ram_data() {return this.ram_data;}
	
	public void set_power_status(boolean opt) { this.power_status = (opt) ? true : false;}
	public void set_id(int tid) {this.id = tid;}
	public void set_name(String tname) {this.name = tname;}
	public void set_cpu_data(int cdata) {this.cpu_data = cdata;}
	public void set_ram_data(int rdata) {this.ram_data = rdata;}
}
