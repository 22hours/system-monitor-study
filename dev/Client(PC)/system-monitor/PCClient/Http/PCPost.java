package PCClient.Http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.google.gson.JsonObject;

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
		URI uri = new URI("http://203.229.204.25:80/pc/5");
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
		JsonObject json = new JsonObject();
		json.addProperty("id", "5");
		json.addProperty("cpu_data", pc.getCpu_data());
		json.addProperty("ram_data", pc.getRam_data());
		json.addProperty("power_status", pc.getPower_status());
		json.addProperty("start_time", pc.getStart_time());
		json.addProperty("end_time", pc.getEnd_time());
		String difference = timeDifference.calc(pc.getStart_time(), pc.getEnd_time());
		Shutdown.getInstance().stopshutdown();
		Shutdown.getInstance().shutdown(difference); // 처음에 예약 설정
		postRequest.setEntity(new StringEntity(json.toString(), "UTF8"));
		postRequest.addHeader("Content-type", "application/json");
		System.out.println("=====POST======");
		System.out.println("id = 5");
		System.out.println("power_status = " + pc.getPower_status());
		System.out.println("end_time = "+pc.getEnd_time());
		System.out.println("cpu_data = "+ pc.getCpu_data());
		System.out.println("start_time = " + pc.getStart_time());
		System.out.println("ram_data = "+pc.getRam_data());
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}

	public void GeneralPollingPost(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://203.229.204.25:80/pc/5");
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		pc.setCpu_data(CPU.getCPU().showCPU());
		pc.setRam_data(Memory.getMemory().showMemory());
		JsonObject json = new JsonObject();
		json.addProperty("id", "5");
		json.addProperty("cpu_data", pc.getCpu_data());
		json.addProperty("ram_data", pc.getRam_data());
		System.out.println("=====POST======");
		System.out.println("id = 5");
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

	public void Extension(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://203.229.204.25:80/pc/" + pc.getId());

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		JsonObject json = new JsonObject();
		json.addProperty("id", "5");
		json.addProperty("end_time", pc.getEnd_time());
		System.out.println("=====POST======");
		System.out.println("id = 5");
		System.out.println("end_time = " + pc.getEnd_time());
		Shutdown.getInstance().stopshutdown();
		TimeDifference timeDifference = new TimeDifference();
		String difference = timeDifference.calc(pc.getStart_time(), pc.getEnd_time());
		Shutdown.getInstance().shutdown(difference);
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
	
	public void PCShutdown(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://203.229.204.25:80/pc/5");
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		JsonObject json = new JsonObject();
		json.addProperty("id", "5");
		json.addProperty("power_status", pc.getPower_status());
		System.out.println("=====POST======");
		System.out.println("id = 5");
		System.out.println("power_status = " + pc.getPower_status());
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
}
