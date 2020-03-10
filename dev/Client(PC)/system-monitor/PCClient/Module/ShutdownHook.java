package PCClient.Module;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import PCClient.Http.PCPost;
import PCModel.PC;

public class ShutdownHook  {
	private PC pc;
	public ShutdownHook(PC pc) {
		this.pc = pc;
	}
	public void AttachShutdownHook() {
		Runtime run = Runtime.getRuntime();
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		String end_time = dayTime.format(new Date(currentTime));
		pc.setEnd_time(end_time);
		try {
			PCPost.getInstance().PostMethod(pc);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
