package com.hours22.system_monitor_ver11.vo;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PcData implements Serializable {
	@JsonProperty("classId")
	private String classId;
	@JsonProperty("powerStatus")
	private String powerStatus;
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("cpuData")
	private String cpuData;
	@JsonProperty("ramData")
	private String ramData;
	@JsonProperty("startTime")
	private String startTime;
	@JsonProperty("endTime")
	private String endTime;
	
	public PcData() {}
	public PcData(String tid) {
		this.id = tid;
		this.powerStatus = "OFF";
		this.name = "Not Name";
		this.cpuData = "0";
		this.ramData = "0";
		this.startTime = "00:00";
		this.endTime = "23:59";
	}
	public String getClassId() {return this.classId;} 
	public String getPowerStatus() {return this.powerStatus;} 
	public String getId() {return this.id;}
	public String getName() {return this.name;}
	public String getCpuData() {return this.cpuData;}
	public String getRamData() {return this.ramData;}
	public String getStartTime() {return this.startTime;}
	public String getEndTime() {return this.endTime;}
	
	public void setClassId(String cid) { this.classId = cid;}
	public void setPowerStatus(String opt) { this.powerStatus = opt;}
	public void setId(String tid) {this.id = tid;}
	public void setName(String tname) {this.name = tname;}
	public void setCpuData(String cdata) {this.cpuData = cdata;}
	public void setRamData(String rdata) {this.ramData = rdata;}
	public void setStartTime(String tstart) {this.ramData = tstart;}
	public void setEndTime(String tend) {this.ramData = tend;}
}