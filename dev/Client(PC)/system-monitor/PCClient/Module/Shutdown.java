package PCClient.Module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shutdown {
	static Runtime run = Runtime.getRuntime();
	public static void shutdown() throws IOException {
		run.exec("shutdown -s -t 10");
		System.out.println("10�� �ڿ� �����ϴ�");
	}
	public static void stopshutdown() throws IOException{
		run.exec("shutdown -a");
		System.out.println("������ ���� ����");
	}
}