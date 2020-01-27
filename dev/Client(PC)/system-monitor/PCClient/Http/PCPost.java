package PCClient.Http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import PCClient.Module.*;
import PCClient.Module.Shutdown;
import PCModel.PC;

public class PCPost {
	private static PCPost instance;
	private PCPost() {
		
	}
	public static PCPost getInstance() {
		if(instance == null) {
			instance = new PCPost();
		}
		return instance;
	}
	public void PostMethod(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://13.125.225.221/pc/"+pc.getId());
		TimeDifference timeDifference = new TimeDifference();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		/*List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", pc.getId()));
		params.add(new BasicNameValuePair("power_status", pc.getPower_status()));
		params.add(new BasicNameValuePair("start_time", pc.getStart_time()));
		params.add(new BasicNameValuePair("end_time", pc.getEnd_time()));
		*/
		/*
		Gson gson = new Gson();
		StringEntity postingString = new StringEntity(gson.toJson(pc));
		postRequest.setEntity(postingString);
		postRequest.setHeader("Content-type","application/json");
		System.out.println(postingString);
		*/
		String cpuData = pc.getCpu_data();
		String ramData = pc.getRam_data();
		JsonObject json = new JsonObject();
		json.addProperty("classId", "1");
		json.addProperty("id", pc.getId());
		json.addProperty("name", "Favian");
		json.addProperty("cpuData", cpuData);
		json.addProperty("ramData", ramData);
		json.addProperty("powerStatus", pc.getPower_status());
		json.addProperty("startTime", pc.getStart_time());
		json.addProperty("endTime", pc.getEnd_time());
		//String difference = timeDifference.calc(pc.getStart_time(), pc.getEnd_time());
		//Shutdown.getInstance().stopshutdown();
		//Shutdown.getInstance().shutdown(difference); // 처음에 예약 설정
		postRequest.setEntity(new StringEntity(json.toString(), "UTF8"));
		postRequest.addHeader("Content-type", "application/json");
		System.out.println("=====POST======");
		System.out.println("classId = 1");
		System.out.println("name = Favian");
		System.out.println("id = "+ pc.getId());
		System.out.println("powerStatus = " + pc.getPower_status());
		System.out.println("endTime = "+pc.getEnd_time());
		System.out.println("cpuData = "+ cpuData);
		System.out.println("startTime = " + pc.getStart_time());
		System.out.println("ramData = "+ramData);
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			try {
				JsonElement jsonElement = JsonParser.parseString(content);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				String id = jsonObject.get("id").getAsString();
				String status = jsonObject.get("powerStatus").getAsString();
				/*if(!id.equals(pc.getId())) {
					System.out.println("잘못된 정보 수신!");
				}
				else {
					System.out.println("컴퓨터를 종료 합니다.");
					PCShutdown(pc);
				}*/
				if(status.equals("off")) { 
					Shutdown.getInstance().shutdown("300"); // 나중에 0으로 고쳐야 함.
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}

	public void GeneralPollingPost(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://13.125.225.221/pc/"+pc.getId());
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		pc.setCpu_data(CPU.getCPU().showCPU());
		pc.setRam_data(Memory.getMemory().showMemory());
		JsonObject json = new JsonObject();
		json.addProperty("id", pc.getId());
		json.addProperty("cpu_data", pc.getCpu_data());
		json.addProperty("ram_data", pc.getRam_data());
		System.out.println("=====POST======");
		System.out.println("id = " + pc.getId());
		System.out.println("cpu_data = " + pc.getCpu_data());
		System.out.println("ram_data = "+pc.getRam_data());
		postRequest.setEntity(new StringEntity(json.toString(), "UTF8"));
		postRequest.addHeader("Content-type", "application/json");
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}

	public void Extension(PC pc, String extensionTime) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://13.125.225.221/pc/" + pc.getId());
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		long tempTime = Integer.parseInt(extensionTime);
		tempTime *= 3600000;
		pc.setEnd_time(dayTime.format(new Date(tempTime)));
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		JsonObject json = new JsonObject();
		json.addProperty("id", "5");
		json.addProperty("end_time", pc.getEnd_time());
		System.out.println("=====POST======");
		System.out.println("id = 5");
		System.out.println("end_time = " + pc.getEnd_time());
/*		Shutdown.getInstance().stopshutdown();
		TimeDifference timeDifference = new TimeDifference();
		String difference = timeDifference.calc(pc.getStart_time(), pc.getEnd_time());
		Shutdown.getInstance().shutdown(difference);*/
		postRequest.setEntity(new StringEntity(json.toString(), "UTF8"));
		postRequest.addHeader("Content-type", "application/json");
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}
	
	/*public void PCShutdown(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		// 프로그램이 꺼지기 전에 post로 보내주기
		URI uri = new URI("http://13.125.225.221/pc/"+pc.getId());
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		pc.setPower_status("false");
		JsonObject json = new JsonObject();
		json.addProperty("id", pc.getId());
		json.addProperty("power_status", pc.getPower_status());
		System.out.println("=====POST======");
		System.out.println("id = " + pc.getId());
		System.out.println("power_status = " + pc.getPower_status());
		postRequest.setEntity(new StringEntity(json.toString(), "UTF8"));
		postRequest.addHeader("Content-type", "application/json");
		Shutdown.getInstance().stopshutdown();
		Shutdown.getInstance().shutdown("300"); // 나중에 0으로 바꿔야 함.
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}*/
}
