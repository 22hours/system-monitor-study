package PCClient.Http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class PCGet {
	private static PCGet instance = null;
	
	private PCGet() {
		
	}
	public static PCGet Instance() {
		if(instance==null) {
			instance = new PCGet();
		}
		return instance;
	}
	public void GetMethod() 
			throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URI("https://jsonplaceholder.typicode.com/posts");
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
		/*HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		System.out.println(content);*/
	}
}
