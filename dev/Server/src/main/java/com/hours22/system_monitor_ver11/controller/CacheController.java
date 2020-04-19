package com.hours22.system_monitor_ver11.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CacheController {
	
	String Etag = "";
	
	public boolean IsCached() {
		if(!Etag.equals(null)) return true;
		else return false;
	}
	
	public String GetCache() {
		return Etag;
	}
	public void SetCache(String etag) {
		this.Etag = etag;
	}
}
