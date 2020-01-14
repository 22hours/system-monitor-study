package PCClient.Module;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDifference {
	public String calc(String original,String extension) {
		try {
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date originalDate = dayTime.parse(original);
			long originalTime = originalDate.getTime();
			Date extensionDate = dayTime.parse(extension);
			long extensionTime = extensionDate.getTime();
			long difference = (extensionTime - originalTime) / 1000;
			if(difference<0) {
				System.out.println("잘못된 정보 수신!");
				return null;
			}
			String time = String.valueOf(difference);
			return time;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
