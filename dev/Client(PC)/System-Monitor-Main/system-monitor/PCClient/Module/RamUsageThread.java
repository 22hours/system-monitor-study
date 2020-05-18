package PCClient.Module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import PCClient.Http.PCGet;
import PCModel.PC;

public class RamUsageThread implements Runnable{
	private JProgressBar ramBar = null;
	private PC pc = null;
	public RamUsageThread(JProgressBar ramBar,PC pc) {
		this.pc = pc;
		this.ramBar = ramBar;
	}
	@Override
	public void run() {
		while(true) {
			ramBar.setValue((int) Double.parseDouble(pc.getRam_data()));
			try {
				Thread.sleep(60000);// 1Ка
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}
