package PCClient.Http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
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
		if (instance == null) {
			instance = new PCPost();
		}
		return instance;
	}

	// �ٽ� ����ϱ� (�����û ���)
	public void PostMethod(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		//URI uri = new URI("http://13.125.208.19/pc/hours23/power/" + pc.getEnd_time());
		URI uri = new URI("http://13.125.208.19/pc/power");
		System.out.println("PostMethod URI = " + uri);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		JsonObject json = new JsonObject();
		//json.addProperty("classId", "D404");
		json.addProperty("id", pc.getId());
		json.addProperty("name", "Favian");
		json.addProperty("cpuData", pc.getCpu_data());
		json.addProperty("ramData", pc.getRam_data());
		json.addProperty("powerStatus", pc.getPower_status());
		json.addProperty("startTime", pc.getStart_time());
		json.addProperty("endTime", pc.getEnd_time());
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8"));
		postRequest.addHeader("Content-type", "application/json");
		System.out.println(json.toString());
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			System.out.println("=====Post Response=====");
			System.out.println(content);
			try {
				JsonElement jsonElement = JsonParser.parseString(content);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				if (jsonObject.get("msg").isJsonNull()) { // PC�� ���� ��û ���� ��
																										// Ȥ�� �ٸ� ���
					System.out.println("-----Post Response is null-----");
				} else {
					String msg = jsonObject.get("msg").getAsString();
					String endTime = jsonObject.get("endTime").getAsString();
					pc.setEnd_time(endTime);
					if(msg.equals("true")) {
						System.out.println("Post ����!");
					}
					else {
						System.out.println("Post ���� ��");
					}
				}
			} catch (Exception e) {
				System.out.println("-----Post Long-Polling Json �м� ����!-----");
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			System.out.println("-----Post Response ����!-----");
			e.printStackTrace();
		}
	}

	public void GeneralPollingPost(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://13.125.208.19/pc/data");
		System.out.println("GeneralPost URI = " + uri);
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpPost postRequest = new HttpPost(uri);
		pc.setCpu_data(CPU.getCPU().showCPU());
		pc.setRam_data(Memory.getMemory().showMemory());
		JsonObject json = new JsonObject();
		json.addProperty("id", pc.getId());
		json.addProperty("cpuData", pc.getCpu_data());
		json.addProperty("ramData", pc.getRam_data());
		System.out.println(json.toString());
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8"));
		postRequest.addHeader("Content-type", "application/json");
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			System.out.println("=====GeneralPost Response=====");
			System.out.println(content);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}

	public void PCShutdown(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		// ���α׷��� ������ ���� post�� �����ֱ�
		URI uri = new URI("http://13.125.208.19/pc/" + pc.getId());
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		JsonObject json = new JsonObject();
		json.addProperty("id", pc.getId());
		json.addProperty("powerStatus", pc.getPower_status());
		System.out.println("=====POST======");
		System.out.println("id = " + pc.getId());
		System.out.println("powerStatus = " + pc.getPower_status());
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8"));
		postRequest.addHeader("Content-type", "application/json");
		System.out.println("=====Shutdown Post Response=====");
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}
}
