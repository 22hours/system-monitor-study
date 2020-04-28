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
		Thread.currentThread().setName("GetLongPolling");
		try {
			while (true) {
				PCGet.getInstance().GetMethod(pc);
				if(pc.getRemainTime().equals("-1")) break;
				/*for(Thread t : Thread.getAllStackTraces().keySet()) {
					if(t.getName().equals("Post")) {
						try {
							System.out.println(t.getState());
							t.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
