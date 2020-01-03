package PCClient.Http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

public class PCGet {
	private static PCGet instance = null;
	private static final String id = "1";

	private PCGet() {

	}

	public static PCGet Instance() {
		if (instance == null) {
			instance = new PCGet();
		}
		return instance;
	}

	public void GetMethod() 
			throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URI("http://172.20.10.10:12345/system_monitor/pc/"+id);
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
				if(power_status.equals("false")) {
					//Shutdown.shutdown(); 
				}
				//endtime 관리 해줘야 한다.
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
