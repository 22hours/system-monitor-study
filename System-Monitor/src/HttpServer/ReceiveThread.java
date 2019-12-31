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

public class ReceiveThread extends Thread{ //������ ���
	private Socket receiveSocket;	
	
	@Override
	public void run() {
		System.out.println("Receive Thread Created!");
		BufferedReader inFromClient = null;
		try {
			inFromClient = new BufferedReader(new InputStreamReader(receiveSocket.getInputStream(),"UTF8"));
			String requestMessageLine = inFromClient.readLine();
			StringTokenizer tokens = new StringTokenizer(requestMessageLine/*,������ �־���� ��*/); // �Ľ� ��ū(������ �ȳ־��ָ� ����Ʈ�� ���� ����)
			if(tokens.nextToken().equals("�����")) {
				// ���� �´� ��� ����
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