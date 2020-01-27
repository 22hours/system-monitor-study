package PCClient.Module;

import PCClient.Http.*;
import PCModel.PC;

public class PostGeneralPolling extends Thread{
	PC pc = null;
	
	public PostGeneralPolling(PC pc) {
		this.pc = pc;
	}
	
	public void run() {
		try {
			while (true) {
				PCPost.getInstance().GeneralPollingPost(pc);
				Thread.sleep(3000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
