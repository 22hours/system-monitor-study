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

public class HttpServer { // Main���� ����

	private Socket c_socket = null;
	
	public HttpServer() {
		try {
			ServerSocket listener = new ServerSocket(8888); // 8080 ��Ʈ
		while(true) { // ���α׷� ����� �� ���� ����
			System.out.println("Waiting...");
			c_socket = listener.accept(); // Block �޼��� (Client�� ���� �� ���� ��������)
			System.out.println("Accept!");
			SendData.getSendData().setSocket(c_socket);
			ReceiveThread receiveThread = new ReceiveThread();
			receiveThread.setSocket(c_socket);
			receiveThread.start(); // �񵿱� Receive
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
