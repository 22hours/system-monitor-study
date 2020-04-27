package com.hours22.system_monitor_ver11.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hours22.system_monitor_ver11.client.ClientInfoController;

@Component
public class CacheController {
	@Autowired
	ClientInfoController cic;
	
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
