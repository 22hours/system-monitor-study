package PCServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import PCClient.Module.CPU;
import PCClient.Module.Memory;

public class GetHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange t) throws IOException {
		// parse request
        Map<String, Object> parameters = new HashMap<String, Object>();
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();
        ParseQuery.parseQuery(query, parameters);

        // send response
        String response = "";
        for (String key : parameters.keySet())
                 response += key + " = " + parameters.get(key) + "\n";
        t.sendResponseHeaders(200, response.length());

		//map은 정렬해서 들어온다.
		response += "22hours CopyRight\n";
		response+=CPU.getCPU().showCPU();
		response+=Memory.getMemory().showMemory();
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();

	}

}
