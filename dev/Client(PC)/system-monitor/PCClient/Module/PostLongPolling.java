package PCClient.Module;

import PCClient.Http.*;
import PCModel.PC;

public class PostLongPolling implements Runnable {
	//private boolean stop = false;
	PC pc = null;

	public PostLongPolling(PC pc) {
		this.pc = pc;
	}

	public void run() {
		try {
			/*Thread.currentThread().setName("PostLongPolling");
			while (!stop) {
				PCPost.getInstance().PostMethod(pc);
				if (pc.getPower_status().equals("OFF"))
					break;
				for(Thread t : Thread.getAllStackTraces().keySet()) {
					if(t.getName().equals("ExtensionThread")) {
						this.stop = true;
						System.out.println(this.stop + "Ȱ��ȭ �Ǿ����ϴ�.");
					}
				}
			}*/
			PCPost.getInstance().PostMethod(pc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
