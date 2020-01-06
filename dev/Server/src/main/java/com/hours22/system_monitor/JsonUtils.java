package com.hours22.system_monitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JsonUtils { 
	
	// GET : Json->Obj (LocalLoad)
	public static PC LocalLoad_JsonToObj(int idx) {

		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String dir = "pcs\\result"+idx+".json";
			
			PC tpc = mapper.readValue(new File(dir), PC.class);
			return tpc;
			
		} catch(JsonGenerationException e){
			e.printStackTrace();
		} catch(JsonMappingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return new PC(-1);
	}
	
	// GET : Obj->Json(HttpResponse)
	public static Map HttpResponse_ObjToJson(PC tpc) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			//객체를 JSON 타입의 String으로 변환.
			String jsonInString = mapper.writeValueAsString(tpc);
			//객체를 JSON 타입의 String으로 변환 및 정렬.
			Map<String, String> map = mapper.readValue(jsonInString, Map.class);
			return map;
			
		}catch (JsonGenerationException e){
			e.printStackTrace();
		}catch (JsonMappingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, String> empty = new HashMap<String, String>();
		return empty;
	}

	
	
	// POST : Json->Obj(HttpRequest)
	public static PC HttpRequest_JsonToObj(int idx) {

		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String dir = "pcs\\result"+idx+".json";
			
			PC tpc = mapper.readValue(new File(dir), PC.class);
			return tpc;
			
		} catch(JsonGenerationException e){
			e.printStackTrace();
		} catch(JsonMappingException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return new PC(-1);
	}
	
	// POST : Obj->Json(LocalSave)
	public static void LocalSave_ObjToJson(PC tpc) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			// 객체를 JSON타입 파일로 변환.
			String dir = "pcs\\result"+tpc.get_id()+".json";
			mapper.writeValue(new File(dir), tpc);

		}catch (JsonGenerationException e){
			e.printStackTrace();
		}catch (JsonMappingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
