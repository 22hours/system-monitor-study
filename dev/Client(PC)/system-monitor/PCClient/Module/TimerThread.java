package PCClient.Module;

import javax.swing.JLabel;

import PCClient.Http.PCGet;
import PCModel.PC;

public class TimerThread implements Runnable{
	private JLabel label = null;
	private PC pc = null;
	public TimerThread(JLabel label,PC pc) {
		this.pc = pc;;
		this.label = label;
	}
	@Override
	public void run() {
		while(true) {
			String nowTime = GetNowTime.getInstance().getNowTime();
			String endTime = pc.getEnd_time();
			String time = TimeDifference.getInstance().timer(nowTime, endTime);
			label.setText(time);
			if(time.equals("00:00")) break;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
