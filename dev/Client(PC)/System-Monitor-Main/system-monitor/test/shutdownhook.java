package test;

import java.io.IOException;

public class shutdownhook extends Thread{
	public void run() {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("shutdown -s -f -t 300");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
