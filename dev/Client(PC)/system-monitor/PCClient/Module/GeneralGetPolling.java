package PCClient.Module;

import PCClient.Http.*;
import PCModel.PC;

public class GeneralGetPolling extends Thread{
	PC pc = null;
	
	public GeneralGetPolling(PC pc) {
		this.pc = pc;
	}
	
	public void run() {
		try {
			while (true) {
				PCGet.Instance().GetMethod(pc);
				Thread.sleep(30000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
