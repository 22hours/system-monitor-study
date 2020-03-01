package PCClient.Module;

import javax.swing.JOptionPane;


public class AlarmThread extends Thread{
	private String message;
	public AlarmThread(String message) {
		this.message = message;
	}
	public void run() {
		try {
			JOptionPane.showMessageDialog(null, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
