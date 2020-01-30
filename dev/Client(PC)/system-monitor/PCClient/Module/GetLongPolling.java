package PCClient.Module;

import PCClient.Http.*;
import PCModel.PC;

public class GetLongPolling extends Thread{
	PC pc = null;
	
	public GetLongPolling(PC pc) {
		this.pc = pc;
	}
	
	public void run() {
		try {
			while (true) {
				PCGet.getInstance().GetMethod(pc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
