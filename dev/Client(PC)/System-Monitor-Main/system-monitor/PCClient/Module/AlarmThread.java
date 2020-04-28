package PCClient.Module;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import sun.applet.Main;


public class AlarmThread extends Thread{
	private String message;
	public AlarmThread(String message) {
		this.message = message;
	}
	public void run() {
		URL dohyeonURL = Main.class.getResource("/img/MapoGoldenPier.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, dohyeonURL.openStream());
		} catch (FontFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			JLabel label = new JLabel("<html><meta charset=\\\"utf-8\\\">" + message + "</html>");
			label.setFont(font.deriveFont(15f));
			JOptionPane.showMessageDialog(null, label);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
