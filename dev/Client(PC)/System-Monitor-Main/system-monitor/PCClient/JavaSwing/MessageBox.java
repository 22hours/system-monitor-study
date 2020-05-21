package PCClient.JavaSwing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import sun.applet.Main;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MessageBox {
	JFrame messageBox = new JFrame();

	private static MessageBox instance = null;
	private MessageBox() {
		initialize();
	}
	
	public static MessageBox getInstance() {
		if(instance==null)
			instance = new MessageBox();
		return instance;
	}
	
	public synchronized void show() {
		messageBox.setLocationRelativeTo(null);
		if(!messageBox.isVisible()) {
			messageBox.setVisible(true);
		}
	}
	
	private void initialize() {
		URL dohyeonURL = getClass().getClassLoader().getResource("MapoGoldenPier.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, dohyeonURL.openStream());
		} catch (FontFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		JPanel upPanel = new JPanel();
		upPanel.setBounds(0, 0, 300, 100); // x y x크기 y크기
		upPanel.setBackground(Color.WHITE);
		upPanel.setLayout(null);
		
		//JLabel message = new JLabel("PC를 종료하시겠습니까?");
		JLabel message = new JLabel("제대로 된 정보를 입력해 주시기 바랍니다.");
		message.setFont(font.deriveFont(15f));
		message.setBounds(17,20,290,25);
		upPanel.add(message);
		
		final MyButton okayButton = new MyButton("확인");
		okayButton.setFont(font.deriveFont(12f));
		okayButton.setFocusPainted(false);
		okayButton.setBackground(new Color(192, 192, 192));
		okayButton.setForeground(Color.WHITE);
		okayButton.setBorder(null);
		okayButton.setHoverBackgroundColor(Color.BLACK);
		okayButton.setPressedBackgroundColor(Color.BLACK);
		okayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageBox.setVisible(false);
			}
		});
		okayButton.setBounds(120, 60, 60, 25);
		upPanel.add(okayButton);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.add(upPanel);
		
		messageBox.getContentPane().add(mainPanel);
		messageBox.setUndecorated(true);
		messageBox.setVisible(false);
		messageBox.setResizable(false);
		messageBox.setSize(300, 100);
		messageBox.setShape(new RoundRectangle2D.Double(0, 0, 300, 100, 10, 10));
	}
}
