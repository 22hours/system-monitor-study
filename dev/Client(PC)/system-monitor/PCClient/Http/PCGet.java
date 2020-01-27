package PCClient.Http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import PCClient.Module.*;
import PCClient.Module.Shutdown;
import PCModel.PC;

public class PCGet {
	private PCGet() {
		
	}
	private static PCGet instance;
	public static PCGet getInstance() {
		if(instance == null) {
			instance = new PCGet();
		}
		return instance;
	}
	public void GetMethod(PC pc) 
			throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URI("http://203.229.204.25:80/pc/5");
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(new HttpGet(uri));
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		try {
			JsonElement jsonElement = JsonParser.parseString(content);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			String id = jsonObject.get("id").getAsString();
			String message = jsonObject.get("message").getAsString();
			if(!message.equals(null)) {
				System.out.println(message);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*public void GetMethod(PC pc) 
			throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URI("http://203.229.204.25:80/pc/5");
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(new HttpGet(uri));
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		try {
			
			JsonElement jsonElement = JsonParser.parseString(content);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			String id = jsonObject.get("id").getAsString();
			String power_status;
			String end_time;
			if(jsonObject.get("power_status").isJsonNull()) {
				power_status = "null";
			}
			else{
				power_status = jsonObject.get("power_status").getAsString();
			}
			if(jsonObject.get("end_time").isJsonNull()) {
				end_time = "null";
			}
			else {
				end_time = jsonObject.get("end_time").getAsString();
			}
			System.out.println("=====GET======");
			System.out.println("id = "+id);
			System.out.println("power_status = " + power_status);
			System.out.println("end_time = "+end_time);
			if(!id.equals("5")) {
				System.out.println("잘못된 정보 수신!");
				return;
			}
			if(!end_time.equals(pc.getEnd_time()) && !end_time.equals("null")) { //Android 사용자가 연장 신청하면 다를 수 있다.
				TimeDifference timeDifference = new TimeDifference();
				Shutdown.getInstance().stopshutdown();
				// Android 사용자가 바꿨다면 end_time이 더 커야 되는게 정상.
				String difference = timeDifference.calc(pc.getEnd_time(), end_time);
				Shutdown.getInstance().shutdown(difference);
				pc.setEnd_time(end_time);
			}
			if(power_status.equals("false") && !power_status.equals("null")) {
				pc.setPower_status(power_status);
				PCPost.getInstance().PCShutdown(pc);
				Shutdown.getInstance().stopshutdown();
				Shutdown.getInstance().shutdown("0");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}*/
}
