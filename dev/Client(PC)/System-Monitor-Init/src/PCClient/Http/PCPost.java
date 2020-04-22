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
	public void PostMethod(String pid, String classId ,int x, int y) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI("http://13.125.208.19/pc/"+pid+"/data");
		System.out.println("PostMethod URI = " + uri);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		JsonObject json = new JsonObject();
		json.addProperty("classId", classId);
		json.addProperty("id", pid);
		json.addProperty("name", "Favian");
		json.addProperty("posR", x);
		json.addProperty("posC", y);
		json.addProperty("powerStatus", "OFF");
		json.addProperty("startTime", "11");
		json.addProperty("endTime", "11");
		json.addProperty("cpuData", "7");
		json.addProperty("ramData", "7");
		json.addProperty("type", "PC");
		postRequest.setEntity(new StringEntity(json.toString(), "UTF-8"));
		postRequest.addHeader("Content-type", "application/json");
		System.out.println(json.toString());
		try {
			HttpResponse response = httpClient.execute(postRequest);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			System.out.println("=====Post Response=====");
			System.out.println(content);
		} catch (ClientProtocolException e) {
			System.out.println("-----Post Response 오류!-----");
			e.printStackTrace();
		}
	}
}
