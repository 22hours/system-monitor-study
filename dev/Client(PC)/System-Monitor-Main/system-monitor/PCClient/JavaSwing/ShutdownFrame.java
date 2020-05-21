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

public class ShutdownFrame {
	JFrame userInfo = new JFrame();
	private static ShutdownFrame instance = null;
	private ShutdownFrame() {
		initialize();
	}
	
	public static ShutdownFrame getInstance() {
		if(instance==null)
			instance = new ShutdownFrame();
		return instance;
	}
	
	public synchronized void show(JFrame jframe) {
		userInfo.setLocationRelativeTo(jframe);
		if(!userInfo.isVisible()) {
			userInfo.setVisible(true);
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
		upPanel.setBounds(0, 0, 200, 100); // x y x크기 y크기
		upPanel.setBackground(Color.WHITE);
		upPanel.setLayout(null);
		
		JLabel message = new JLabel("PC를 종료하시겠습니까?");
		message.setFont(font.deriveFont(15f));
		message.setBounds(20,20,200,25);
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
				userInfo.setVisible(false);
				System.exit(0);
			}
		});
		okayButton.setBounds(30, 60, 60, 25);
		upPanel.add(okayButton);
		
		final MyButton noButton = new MyButton("취소");
		noButton.setFont(font.deriveFont(12f));
		noButton.setFocusPainted(false);
		noButton.setBackground(new Color(192, 192, 192));
		noButton.setForeground(Color.WHITE);
		noButton.setBorder(null);
		noButton.setHoverBackgroundColor(Color.BLACK);
		noButton.setPressedBackgroundColor(Color.BLACK);
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userInfo.setVisible(false);
			}
		});
		noButton.setBounds(110, 60, 60, 25);
		upPanel.add(noButton);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.add(upPanel);
		
		userInfo.getContentPane().add(mainPanel);
		userInfo.setUndecorated(true);
		userInfo.setVisible(false);
		userInfo.setResizable(false);
		userInfo.setSize(200, 100);
		userInfo.setShape(new RoundRectangle2D.Double(0, 0, 200, 100, 10, 10));
	}
}
