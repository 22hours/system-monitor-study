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
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import PCClient.Module.CPU;
import PCClient.Module.Memory;

public class PCPost {
	private static PCPost instance = null;
	private static final String id = "1";
	private PCPost() {
		
	}
	public static PCPost Instance() {
		if(instance==null) {
			instance = new PCPost();
		}
		return instance;
	}
	public void PostMethod() 
			throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URI("http://172.20.10.10:12345/system_monitor/");
		System.out.println(uri);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id",id));
		params.add(new BasicNameValuePair("power_status","true"));
		params.add(new BasicNameValuePair("start_time","18:00"));
		params.add(new BasicNameValuePair("end_time","21:00"));
		try {
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,"UTF-8");
			postRequest.setEntity(ent);
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
		HttpResponse response = httpClient.execute(postRequest);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		System.out.println(content);
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}
	public void GeneralPollingPost() 
			throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URI("http://172.20.10.10:12345/"+id);
		System.out.println(uri);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id",id));
		params.add(new BasicNameValuePair("cpu_data",CPU.getCPU().showCPU()));
		params.add(new BasicNameValuePair("ram_data",Memory.getMemory().showMemory()));
		try {
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,"UTF-8");
			postRequest.setEntity(ent);
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
		HttpResponse response = httpClient.execute(postRequest);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		System.out.println(content);
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}
	public void Extension() 
			throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URI("http://172.20.10.10:12345/"+id);
		System.out.println(uri);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id",id));
		params.add(new BasicNameValuePair("end_time","23:00"));
		try {
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,"UTF-8");
			postRequest.setEntity(ent);
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
		HttpResponse response = httpClient.execute(postRequest);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		System.out.println(content);
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
	}
}
