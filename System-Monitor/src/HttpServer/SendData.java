package HttpServer;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SendData {
	private static SendData SD = new SendData(); // �̱���
	private Socket sendSocket;
	private SendData() {
		
	}
	public static SendData getSendData() {
		return SD;
	}
	public void setSocket(Socket socket) {
		sendSocket = socket;
	}
	public void Send() {
		DataOutputStream outToClient = null;
		try {
			outToClient = new DataOutputStream(sendSocket.getOutputStream());
			outToClient.writeBytes("����ٰ� ����� �ֱ�\r\n");
			System.out.println("Send Complete");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
