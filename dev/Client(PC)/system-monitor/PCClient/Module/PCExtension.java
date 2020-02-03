package PCClient.Module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import PCModel.PC;
import PCClient.Http.PCPost;

public class PCExtension {
	public void Extension(PC pc, long hours) {
		hours = hours*3600*1000;
		try {
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			Date originalDate = dayTime.parse(pc.getEnd_time());
			long extensionTime = originalDate.getTime() + hours;
			String extension = dayTime.format(new Date(extensionTime));
			pc.setEnd_time(extension);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			//PCPost.getInstance().Extension(pc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
