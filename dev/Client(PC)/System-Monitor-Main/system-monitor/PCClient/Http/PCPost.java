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

	// 다시 사용하기 (연장신청 대비)
	public void PostMethod(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		//URI uri = new URI("http://13.125.208.19/pc/hours23/power/" + pc.getEnd_time());
		URI uri = new URI("http://13.125.208.19/pc/power");
		System.out.println("PostMethod URI = " + uri);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		JsonObject json = new JsonObject();
		json.addProperty("classId", pc.getClass_id());
		json.addProperty("id", pc.getId());
		json.addProperty("posR", pc.getPosR());
		json.addProperty("posC", pc.getPosC());
		json.addProperty("name", pc.getName());
		json.addProperty("cpuData", pc.getCpu_data());
		json.addProperty("ramData", pc.getRam_data());
		json.addProperty("powerStatus", pc.getPower_status());
		json.addProperty("startTime", pc.getStart_time());
		json.addProperty("endTime", pc.getEnd_time());
		json.addProperty("type", pc.getType());
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8"));
		postRequest.addHeader("Content-type", "application/json");
		System.out.println("Post Body = " + json.toString());
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			System.out.println("=====Post Response=====");
			System.out.println(content);
			try {
				JsonElement jsonElement = JsonParser.parseString(content);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				if (jsonObject.get("msg").isJsonNull()) { // PC가 연장 신청 했을 때
																										// 혹은 다른 경우
					System.out.println("-----Post Response is null-----");
				} else {
					String msg = jsonObject.get("msg").getAsString();
					if(msg.equals("false")) { 
						System.out.println("Post 실패 ㅠ");
						return;
					}
					String endTime = jsonObject.get("endTime").getAsString();
					pc.setEnd_time(endTime);
					if(msg.equals("true")) {
						System.out.println("Post 성공!");
					}
					else {
						System.out.println("Post 실패 ㅠ");
					}
				}
			} catch (Exception e) {
				System.out.println("-----Post Long-Polling Json 분석 오류!-----");
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			System.out.println("-----Post Response 오류!-----");
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
		System.out.println("General Polling Post Body = " + json.toString());
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

	public void InitPost(String classID, int posR, int posC) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://13.125.208.19/pc/data");
		String name = classID + "_" + posR + "_" +posC;
		System.out.println("InitPost URI = " + uri);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		JsonObject json = new JsonObject();
		json.addProperty("classId", classID);
		json.addProperty("id", name);
		json.addProperty("posR", posR);
		json.addProperty("posC", posC);
		json.addProperty("name", name);
		json.addProperty("type", "PC");
		System.out.println("Init Post Body = " + json.toString());
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8"));
		postRequest.addHeader("Content-type", "application/json");
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			System.out.println("=====Init Post Response=====");
			System.out.println(content);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}
}
