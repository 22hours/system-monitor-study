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
		URI uri = new URI("http://13.125.208.19/pc/hours22/power/" + pc.getEnd_time());
		System.out.println("PostMethod URI = " + uri);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		JsonObject json = new JsonObject();
		json.addProperty("classId", "1");
		json.addProperty("id", "hours22");
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
			System.out.println("=====Post Long-Polling Response=====");
			System.out.println(content);
			try {
				JsonElement jsonElement = JsonParser.parseString(content);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				if (jsonObject.get("msg").isJsonNull()) { // PC가 연장 신청 했을 때
																										// 혹은 다른 경우
					System.out.println("-----Post Long-Polling Response is null-----");
				} else {
					String msg = jsonObject.get("msg").getAsString();
					if(msg.equals("true")) {
						System.out.println("Post 성공!");
					}
					else {
						System.out.println("Post 실패 ㅠ");
					}
					/*String power_status = jsonObject.get("powerStatus").getAsString();
					String end_time = jsonObject.get("endTime").getAsString();
					if (!pc.getPower_status().equals("OFF") && power_status.equals("OFF")) {
						Shutdown.getInstance().shutdown("300"); // 나중에 0으로 고쳐야 함.
					}
					pc.setEnd_time(end_time);
					pc.setPower_status(power_status);*/
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
		URI uri = new URI("http://13.125.208.19/pc/" + "hours22" + "/data");
		System.out.println("GeneralPost URI = " + uri);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		pc.setCpu_data(CPU.getCPU().showCPU());
		pc.setRam_data(Memory.getMemory().showMemory());
		JsonObject json = new JsonObject();
		json.addProperty("id", "hours22");
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
			try {
				JsonElement jsonElement = JsonParser.parseString(content);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				if (jsonObject.get("msg").isJsonNull()) { // PC가 연장 신청 했을 때
																										// 혹은 다른 경우
					System.out.println("-----GeneralPost Response is null-----");
				} else {
					String msg = jsonObject.get("msg").getAsString();
					if(msg.equals("true")) {
						System.out.println("GeneralPost 성공!");
					}
					else {
						System.out.println("GeneralPost 실패 ㅠ");
					}
				}
			} catch (Exception e) {
				System.out.println("-----GeneralPost Json 분석 오류!-----");
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void Extension(PC pc, String extensionTime) throws URISyntaxException,
	 * ClientProtocolException, IOException { SimpleDateFormat dayTime = new
	 * SimpleDateFormat("yyyy-MM-dd-HH-mm"); try { Date endTimeDate =
	 * dayTime.parse(pc.getEnd_time()); long time = endTimeDate.getTime(); long
	 * tempTime = Integer.parseInt(extensionTime); // tempTime *= 3600000; tempTime
	 * *= 60000; // 1분 늘리기 time += tempTime; pc.setEnd_time(dayTime.format(new
	 * Date(time))); System.out.println(dayTime.format(new Date(time))); } catch
	 * (ParseException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); } URI uri = new URI("http://13.125.225.221/pc/" +
	 * "damin" + "/power/" + pc.getEnd_time());
	 * System.out.println("uri = http://13.125.225.221/pc/damin/power/" +
	 * pc.getEnd_time()); HttpClient httpClient =
	 * HttpClientBuilder.create().build(); HttpPost postRequest = new HttpPost(uri);
	 * JsonObject json = new JsonObject(); json.addProperty("id", "damin");
	 * json.addProperty("endTime", pc.getEnd_time());
	 * System.out.println("=====POST======"); System.out.println("id = damin");
	 * System.out.println("endTime = " + pc.getEnd_time());
	 * postRequest.setEntity(new StringEntity(json.toString(), "UTF-8"));
	 * postRequest.addHeader("Content-type", "application/json"); try { HttpResponse
	 * response = httpClient.execute(postRequest); HttpEntity entity =
	 * response.getEntity(); String content = EntityUtils.toString(entity);
	 * System.out.println("=====Post Extension Response=====");
	 * System.out.println(content); try { JsonElement jsonElement =
	 * JsonParser.parseString(content); JsonObject jsonObject =
	 * jsonElement.getAsJsonObject(); String id =
	 * jsonObject.get("id").getAsString(); String status =
	 * jsonObject.get("powerStatus").getAsString(); /* if(!id.equals(pc.getId())) {
	 * System.out.println("잘못된 정보 수신!"); } else {
	 * System.out.println("컴퓨터를 종료 합니다."); PCShutdown(pc); }
	 * 
	 * if (status.equals("OFF")) { Shutdown.getInstance().shutdown("300"); // 나중에
	 * 0으로 고쳐야 함. } } catch (Exception e) { e.printStackTrace(); } } catch
	 * (ClientProtocolException e) { e.printStackTrace(); } }
	 */

	public void PCShutdown(PC pc) throws URISyntaxException, ClientProtocolException, IOException {
		// 프로그램이 꺼지기 전에 post로 보내주기
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
