package com.hours22.system_monitor;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Autowired;


@ToString
public class PcData implements Serializable {
	private String power_status;
	private String id;
	private String name;
	private String cpu_data;
	private String ram_data;
	
	public PcData() {}
	public PcData(String tid) {
		this.id = tid;
		this.power_status = "OFF";
		this.name = "Not Name";
		this.cpu_data = "0";
		this.ram_data = "0";
	}
	public String get_power_status() {return this.power_status;} 
	public String get_id() {return this.id;}
	public String get_name() {return this.name;}
	public String get_cpu_data() {return this.cpu_data;}
	public String get_ram_data() {return this.ram_data;}
	
	public void set_power_status(String opt) { this.power_status = opt;}
	public void set_id(String tid) {this.id = tid;}
	public void set_name(String tname) {this.name = tname;}
	public void set_cpu_data(String cdata) {this.cpu_data = cdata;}
	public void set_ram_data(String rdata) {this.ram_data = rdata;}
}