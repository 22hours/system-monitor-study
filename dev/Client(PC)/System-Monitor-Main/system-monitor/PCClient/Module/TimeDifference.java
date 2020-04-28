package PCClient.Module;

import java.text.ParseException;
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
			System.out.println("Difference = " + difference);
			if(difference<0) {
				return "0";
			}
			if(difference/3600 >= 1) return "60";
			String hour = String.valueOf(difference/3600);
			difference%=3600;
			String minute = String.valueOf(difference/60);
			difference%=60;
			return minute;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return "0";
	}
	public String timer(String nowTime,String endTime) {
		try {
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			Date originalDate = dayTime.parse(nowTime);
			long originalTime = originalDate.getTime();
			Date extensionDate = dayTime.parse(endTime);
			long extensionTime = extensionDate.getTime();
			long difference = (extensionTime - originalTime)/1000;
			String hour = String.valueOf(difference/3600);
			difference%=3600;
			String minute = String.valueOf(difference/60);
			difference%=60;
			if(Integer.parseInt(hour)<10) {
				hour = "0" + hour;
			}
			if(Integer.parseInt(minute)<10) {
				minute = "0" + minute;
			}
			return hour + " : " + minute;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
}
