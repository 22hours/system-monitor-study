package PCClient.Module;

import PCClient.Http.*;
import PCModel.PC;

public class PostGeneralPolling implements Runnable{
	PC pc = null;
	
	public PostGeneralPolling(PC pc) {
		this.pc = pc;
	}
	
	public void run() {
		try {
			while (true) {
				PCPost.getInstance().GeneralPollingPost(pc);
				Thread.sleep(60000); // 1Ка
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
