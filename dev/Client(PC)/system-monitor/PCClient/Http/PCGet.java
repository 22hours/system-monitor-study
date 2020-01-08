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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import PCClient.Module.*;
import PCModel.PC;

public class PCGet {
	private static PCGet instance = null;

	private PCGet() {

	}

	public static PCGet Instance() {
		if (instance == null) {
			instance = new PCGet();
		}
		return instance;
	}

	public void GetMethod(PC pc) 
			throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URI("http://172.20.10.10:12345/system_monitor/pc/"+ pc.getId());
		System.out.println(uri);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(new HttpGet(uri));
		/*if (response.getStatusLine().getStatusCode() == 200) {
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			System.out.println(body);
		} else {
			System.out.println("response is error : " + response.getStatusLine().getStatusCode());
		}*/
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		try {
			JsonParser jsonParser = new JsonParser();
			JsonArray jsonArray = (JsonArray)jsonParser.parse(content);
			for(int i=0;i<jsonArray.size();i++) {
				JsonObject object = (JsonObject)jsonArray.get(i);
				String id = object.get("id").getAsString();
				String power_status = object.get("power_status").getAsString();
				String end_time = object.get("end_time").getAsString();
				if(!pc.getId().equals(id)) {
					System.out.println("잘못된 정보 수신!");
					return;
				}
				if(power_status.equals("false") || end_time.equals(pc.getEnd_time())) {
					pc.setPower_status(false);
					Shutdown.stopshutdown(); // 예약이 있을 수도 있으니 취소
					Shutdown.shutdown("0"); 
					return;
				}
				if(!end_time.equals(pc.getEnd_time())) { //Android 사용자가 연장 신청하면 다를 수 있다.
					Shutdown.stopshutdown();
					// Android 사용자가 바꿨다면 end_time이 더 커야 되는게 정상.
					String difference = TimeDifference.calc(pc.getEnd_time(), end_time);
					Shutdown.shutdown(difference);
					pc.setEnd_time(end_time);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
