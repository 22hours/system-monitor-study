package PCClient.Module;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class AlarmThread extends Thread{
	private String message;
	public AlarmThread(String message) {
		this.message = message;
	}
	public void run() {
		try {
			JLabel label = new JLabel("<html><meta charset=\\\"utf-8\\\">" + message + "</html>");
			label.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
			JOptionPane.showMessageDialog(null, label);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
