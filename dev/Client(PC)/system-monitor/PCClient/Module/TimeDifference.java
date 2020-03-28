package PCClient.Module;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class TimeDifference {
	private static TimeDifference instance;
	
	private TimeDifference() {
		
	}
	
	public static TimeDifference getInstance() {
		if(instance==null)
		{
			instance = new TimeDifference();
		}
		return instance;
	}
	public String calc(String original,String extension) {
		try {
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			Date originalDate = dayTime.parse(original);
			long originalTime = originalDate.getTime();
			Date extensionDate = dayTime.parse(extension);
			long extensionTime = extensionDate.getTime();
			long difference = (extensionTime - originalTime) / 1000;
			if(difference<0) {
				return null;
			}
			String hour = String.valueOf(difference/3600);
			difference%=3600;
			String minute = String.valueOf(difference/60);
			difference%=60;
			return hour+"시간 " + minute + "분  남았습니다.";
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
