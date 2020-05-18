package PCClient.Module;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import PCClient.Http.PCPost;
import PCModel.PC;

public class ShutdownHook {
	private PC pc;

	public ShutdownHook(PC pc) {
		this.pc = pc;
	}

	public void AttachShutdownHook() {
		System.out.println("ShutdownHook attached!");
		/*
		 * Runtime.getRuntime().addShutdownHook(new Thread() {
		 * 
		 * @Override public void run() { Runtime run = Runtime.getRuntime(); try {
		 * run.exec("shutdown -s -f -t 300"); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } });
		 */
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("ShutdownHook 시작!");
				Runtime run = Runtime.getRuntime();
				long currentTime = System.currentTimeMillis();
				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
				String end_time = dayTime.format(new Date(currentTime));
				pc.setEnd_time(end_time);
				pc.setPower_status("OFF");				
				try {
					PCPost.getInstance().PostMethod(pc);
				} catch (URISyntaxException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("종료 됩니다.");
				/*try {
					Shutdown.getInstance().shutdown("0");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			}
		});
	}
}
