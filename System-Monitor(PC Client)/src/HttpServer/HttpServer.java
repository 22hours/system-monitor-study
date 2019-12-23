package HttpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.HttpsURLConnection;

public class HttpServer { // Main에서 생성

	private Socket c_socket = null;
	
	public HttpServer() {
		try {
			ServerSocket listener = new ServerSocket(8888); // 8080 포트
		while(true) { // 프로그램 종료될 떄 까지 실행
			System.out.println("Waiting...");
			c_socket = listener.accept(); // Block 메서드 (Client가 들어올 때 까지 멈춰있음)
			System.out.println("Accept!");
			SendData.getSendData().setSocket(c_socket);
			ReceiveThread receiveThread = new ReceiveThread();
			receiveThread.setSocket(c_socket);
			receiveThread.start(); // 비동기 Receive
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
