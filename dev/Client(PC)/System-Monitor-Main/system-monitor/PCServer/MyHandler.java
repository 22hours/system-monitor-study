package PCServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange t) throws IOException {
		Map<String,String> query = parseQueryString(t.getRequestURI().getRawQuery());
		//Ex) localhost:8888/22hours/damin/handsomeguy?he=good&damin=handsome
		
		//getRequestURI = /22hours/damin/handsomeguy?he=good&damin=handsome
		//getRawQuery = he=good&damin=handsome
		
		System.out.println("#"+query+"#");
		//map은 정렬해서 들어온다.
		//map에 있는 query마다 알맞은 기능 수행
		String response = "This is the response";
		// response 메서드 짜면 될 듯
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	private Map<String,String> parseQueryString(String queryString){
		Map<String,String> param = new HashMap<String,String>();
		if(queryString==null) return param;
		String[] queryParts = queryString.split("[&]");
		for(String queryPart : queryParts) {
			int idx = queryPart.indexOf('=');
			if(idx<0) {
				param.put(decode(queryPart), null);
			}
			else {
				param.put(decode(queryPart.substring(0,idx)),
						decode(queryPart.substring(idx+1)));
			}
		}
		return param;
	}
	private String decode(String str) {
		try {
			return URLDecoder.decode(str,"UTF-8");
		}
		catch(UnsupportedEncodingException e) {
			return "?";
		}
		
	}
}
