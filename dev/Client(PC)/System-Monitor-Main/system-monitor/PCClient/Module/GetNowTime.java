package PCClient.Module;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetNowTime {
	private GetNowTime() {
		
	}
	private static GetNowTime instance;
	public static GetNowTime getInstance() {
		if(instance == null) {
			instance = new GetNowTime();
			
		}
		return instance;
	}
	public String getNowTime() {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		return dayTime.format(new Date(time));
	}
}
