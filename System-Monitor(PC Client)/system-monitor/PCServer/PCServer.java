package PCServer;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class PCServer { 
	
	public PCServer() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8888),0);
					//8888��Ʈ, "/22hours" ���ؽ�Ʈ�� ����
			server.createContext("/22hoursG", new GetHandler()); // get
			server.createContext("/22hoursP", new PostHandler()); // post
			server.setExecutor(null);
			server.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
