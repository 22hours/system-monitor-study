package PCClient.Module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shutdown {
	public static void shutdown() throws IOException {
		Runtime run = Runtime.getRuntime();
		Process pro = run.exec("shutdown -s -t 0");
		System.exit(0);
	}

}