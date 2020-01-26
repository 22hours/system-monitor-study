package PCClient.Module;

import PCClient.Http.*;
import PCModel.PC;

public class GeneralPostPolling extends Thread{
	PC pc = null;
	
	public GeneralPostPolling(PC pc) {
		this.pc = pc;
	}
	
	public void run() {
		try {
			while (true) {
				PCPost.getInstance().GeneralPollingPost(pc);
				Thread.sleep(30000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
