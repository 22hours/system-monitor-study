package PCClient.Module;

import PCClient.Http.PCGet;
import PCClient.Http.PCPost;
import PCModel.PC;

public class ExtensionThread implements Runnable{
	private PC pc = null;
	private String time;
	public ExtensionThread(PC pc,String time) {
		this.pc = pc;
		this.time = time;
	}
	public void run() {
		try {
			while(true)
				PCPost.getInstance().PostMethod(pc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
