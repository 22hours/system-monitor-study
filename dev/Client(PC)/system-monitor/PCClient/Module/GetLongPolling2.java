package PCClient.Module;

import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import PCClient.Http.PCGet;
import PCClient.Http.PCPost;
import PCModel.PC;


public class GetLongPolling2 extends Thread{
	PC pc = null;

	public GetLongPolling2(PC pc) {
		this.pc = pc;
	}
	public void run() {
		Thread.currentThread().setName("GetLongPolling");
		try {
			while (true) {
				PCGet.getInstance().GetMethod(pc);
				if(pc.getRemainTime().equals("-1")) break;
				/*for(Thread t : Thread.getAllStackTraces().keySet()) {
					if(t.getName().equals("Post")) {
						try {
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
