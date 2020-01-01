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

public class PCPost {
	private static PCPost instance = null;
	
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
		URI uri = new URI("https://jsonplaceholder.typicode.com/posts");
		System.out.println(uri);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("title","damin"));
		params.add(new BasicNameValuePair("body","22hours"));
		params.add(new BasicNameValuePair("userId","1"));
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
