package PCClient.Module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import PCClient.Http.PCGet;
import PCModel.PC;

public class CPUUsageThread implements Runnable{
	private JProgressBar cpuBar = null;
	private PC pc = null;
	public CPUUsageThread(JProgressBar cpuBar,PC pc) {
		this.pc = pc;
		this.cpuBar = cpuBar;
	}
	@Override
	public void run() {
		while(true) {
			cpuBar.setValue((int) Double.parseDouble(pc.getCpu_data()));
			try {
				Thread.sleep(60000);// 1Ка
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}
