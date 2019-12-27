package PCServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PCServer { 
	
	public PCServer() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8888),0);
					//8888포트, "/22hours" 컨텍스트로 서비스
			server.createContext("/22hours", new MyHandler());
			server.setExecutor(null);
			server.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
