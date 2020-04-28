package PCClient.Module;

import PCClient.Http.PCGet;
import PCClient.Http.PCPost;
import PCModel.PC;

public class ExtensionThread implements Runnable {
	private boolean stop = false;
	private PC pc = null;
	private String time;

	public ExtensionThread(PC pc, String time) {
		this.pc = pc;
		this.time = time;
	}

	public void run() {
		/*try {
			Thread.currentThread().setName("ExtensionThread");
			while (!stop) {
				PCPost.getInstance().PostMethod(pc);
				if (pc.getPower_status().equals("OFF"))
					break;
				for(Thread t : Thread.getAllStackTraces().keySet()) {
					if(t.getName().equals("PostLongPolling")) {
						this.stop = true;
						System.out.println(this.stop + "활성화 되었습니다.");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
