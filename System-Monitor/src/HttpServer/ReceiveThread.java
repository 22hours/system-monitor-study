package HttpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class ReceiveThread extends Thread{ //쓰레드 상속
	private Socket receiveSocket;	
	
	@Override
	public void run() {
		System.out.println("Receive Thread Created!");
		BufferedReader inFromClient = null;
		try {
			inFromClient = new BufferedReader(new InputStreamReader(receiveSocket.getInputStream(),"UTF8"));
			String requestMessageLine = inFromClient.readLine();
			StringTokenizer tokens = new StringTokenizer(requestMessageLine/*,구분자 넣어줘야 함*/); // 파싱 토큰(구분자 안넣어주면 디폴트는 공백 문자)
			if(tokens.nextToken().equals("예약어")) {
				// 예약어에 맞는 기능 수행
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setSocket(Socket socket) {
		this.receiveSocket = socket;
	}
}