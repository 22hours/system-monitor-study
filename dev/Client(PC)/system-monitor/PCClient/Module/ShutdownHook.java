package PCClient.Module;

import java.io.IOException;
import java.net.URISyntaxException;

import PCClient.Http.PCPost;
import PCModel.PC;

public class ShutdownHook {
	PC pc;
	public ShutdownHook(PC pc) {
		this.pc = pc;
	}
	public void AttachShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					pc.setPower_status("OFF");
					PCPost.getInstance().PCShutdown(pc);
				} catch (URISyntaxException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		System.out.println("Shut Down Hook Attached...");
	}
}
