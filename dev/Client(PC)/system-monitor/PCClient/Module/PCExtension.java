package PCClient.Module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import PCModel.PC;
import sun.reflect.generics.tree.IntSignature;
import PCClient.Http.PCPost;

public class PCExtension {
	private static PCExtension instance;
	private PCExtension() {}
	public static PCExtension getInstance() {
		if(instance == null) {
			instance = new PCExtension();
		}
		return instance;
	}
	public void Extension(PC pc, String extensionTime) {
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		try {
			Date endTimeDate = dayTime.parse(pc.getEnd_time());
			long time = endTimeDate.getTime();
			long tempTime = Integer.parseInt(extensionTime);
			tempTime *= 3600000;
			//tempTime *= 60000; // 1ºÐ ´Ã¸®±â
			time += tempTime;
			pc.setEnd_time(dayTime.format(new Date(time)));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//PCPost.getInstance().Extension(pc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
