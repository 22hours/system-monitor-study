package PCClient.Module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

import PCClient.Http.PCGet;
import PCModel.PC;

public class EndTimeThread implements Runnable{
	private JLabel label = null;
	private PC pc = null;
	public EndTimeThread(JLabel label,PC pc) {
		this.pc = pc;;
		this.label = label;
	}
	@Override
	public void run() {
		while(true) {
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			String endTime = null;
			try {
				Date end_Time = dayTime.parse(pc.getEnd_time());
				long end_Time1 = end_Time.getTime();
				dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				endTime = dayTime.format(new Date(end_Time1));
				label.setText(endTime);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
}
