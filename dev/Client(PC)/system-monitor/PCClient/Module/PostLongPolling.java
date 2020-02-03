package PCClient.Module;

import PCClient.Http.*;
import PCModel.PC;

public class PostLongPolling extends Thread{
	PC pc = null;
	
	public PostLongPolling(PC pc) {
		this.pc = pc;
	}
	
	public void run() {
		try {
			while (true) {
				PCPost.getInstance().PostMethod(pc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
