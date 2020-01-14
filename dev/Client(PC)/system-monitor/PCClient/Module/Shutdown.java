package PCClient.Module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Shutdown {
	Runtime run = Runtime.getRuntime();
	public void shutdown(String time) throws IOException {
		run.exec("shutdown -s -t "+ time);
	}
	public void stopshutdown() throws IOException{
		run.exec("shutdown -a");
	}
}