package com.hours22.system_monitor;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;


@ToString
public class PcData implements Serializable {
	@JsonProperty("class_id")
	private String class_id;
	@JsonProperty("power_status")
	private String power_status;
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("cpu_data")
	private String cpu_data;
	@JsonProperty("ram_data")
	private String ram_data;
	@JsonProperty("start_time")
	private String start_time;
	@JsonProperty("end_time")
	private String end_time;
	
	public PcData() {}
	public PcData(String tid) {
		this.id = tid;
		this.power_status = "OFF";
		this.name = "Not Name";
		this.cpu_data = "0";
		this.ram_data = "0";
		this.start_time = "00:00";
		this.end_time = "23:59";
	}
	public String get_class_id() {return this.class_id;} 
	public String get_power_status() {return this.power_status;} 
	public String get_id() {return this.id;}
	public String get_name() {return this.name;}
	public String get_cpu_data() {return this.cpu_data;}
	public String get_ram_data() {return this.ram_data;}
	public String get_start_time() {return this.start_time;}
	public String get_end_time() {return this.end_time;}
	
	public void set_class_id(String cid) { this.class_id = cid;}
	public void set_power_status(String opt) { this.power_status = opt;}
	public void set_id(String tid) {this.id = tid;}
	public void set_name(String tname) {this.name = tname;}
	public void set_cpu_data(String cdata) {this.cpu_data = cdata;}
	public void set_ram_data(String rdata) {this.ram_data = rdata;}
	public void set_start_time(String tstart) {this.ram_data = tstart;}
	public void set_end_time(String tend) {this.ram_data = tend;}
}