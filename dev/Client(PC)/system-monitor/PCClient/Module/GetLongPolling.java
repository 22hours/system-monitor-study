package PCClient.Module;

import PCClient.Http.*;
import PCModel.PC;

public class GetLongPolling implements Runnable {
	PC pc = null;

	public GetLongPolling(PC pc) {
		this.pc = pc;
	}

	@Override
	public void run() {
		try {
			while (true) {
				PCGet.getInstance().GetMethod(pc);
				if(pc.getRemainTime().equals("-1")) break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
