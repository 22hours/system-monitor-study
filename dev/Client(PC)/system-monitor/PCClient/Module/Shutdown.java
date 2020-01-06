package PCClient.Module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shutdown {
	static Runtime run = Runtime.getRuntime();
	public static void shutdown() throws IOException {
		run.exec("shutdown -s -t 10");
		System.out.println("10초 뒤에 꺼집니다");
	}
	public static void stopshutdown() throws IOException{
		run.exec("shutdown -a");
		System.out.println("꺼지는 예약 종료");
	}
}