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

import PCModel.PC;
import sun.applet.Main;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserInfo {
	JFrame userInfo = new JFrame();
	PC pc = null;
	private Boolean isit = false;
	private static UserInfo instance = null;
	private UserInfo() {
	}
	
	public static UserInfo getInstance() {
		if(instance==null)
			instance = new UserInfo();
		return instance;
	}
	
	public synchronized void show(JFrame jframe,PC pc) {
		this.pc = pc;
		if(isit==false)
			initialize();
		userInfo.setLocationRelativeTo(null);
		if(!userInfo.isVisible()) {
			userInfo.setVisible(true);
		}
	}
	
	private void initialize() {
		isit = true;
		URL dohyeonURL = Main.class.getResource("/img/MapoGoldenPier.ttf");
		URL logoURL = Main.class.getResource("/img/logo.png");
		URL onURL = Main.class.getResource("/img/on.png");
		URL offURL = Main.class.getResource("/img/off.png");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, dohyeonURL.openStream());
		} catch (FontFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		JPanel upPanel = new JPanel();
		upPanel.setBounds(0, 0, 320, 250); // x y x크기 y크기
		upPanel.setBackground(Color.WHITE);
		upPanel.setLayout(null);
		
		ImageIcon logoImage = new ImageIcon(logoURL);
		JLabel logo = new JLabel(logoImage);
		logo.setBounds(0, 0, logoImage.getIconWidth(), logoImage.getIconHeight());
		upPanel.add(logo);
		
		JLabel startTime = new JLabel(pc.getStart_time());
		startTime.setFont(font.deriveFont(15f));
		startTime.setBounds(120,20,180,22); // x위치 y위치 x크기 y크기
		upPanel.add(startTime);
		
		JLabel endTime = new JLabel(pc.getEnd_time());
		endTime.setFont(font.deriveFont(15f));
		endTime.setBounds(120,52,180,22); // x위치 y위치 x크기 y크기
		upPanel.add(endTime);
		
		JLabel on = new JLabel("ON");
		on.setFont(font.deriveFont(12f));
		on.setForeground(Color.GRAY);
		on.setBounds(265,25,50,15); 
		upPanel.add(on);
		
		ImageIcon onImage = new ImageIcon(onURL);
		JLabel onIcon = new JLabel(onImage);
		onIcon.setBounds(100, 25, 10, 10);
		upPanel.add(onIcon);
		
		JLabel off = new JLabel("OFF");
		off.setFont(font.deriveFont(12f));
		off.setForeground(Color.GRAY);
		off.setBounds(265,57,50,15); 
		upPanel.add(off);
		
		ImageIcon offImage = new ImageIcon(offURL);
		JLabel offIcon = new JLabel(offImage);
		offIcon.setBounds(100, 57, 10, 10);
		upPanel.add(offIcon);
		
		UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
		UIManager.put("ProgressBar.selectionForeground", Color.BLACK);

		JProgressBar cpuBar = new JProgressBar(0, 100);
		cpuBar.setValue((int) Double.parseDouble(pc.getCpu_data()));
		cpuBar.setStringPainted(true);
		cpuBar.setForeground(new Color(255,222,173));
		cpuBar.setBackground(new Color(255,250,240));
		//cpuBar.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
		cpuBar.setBorderPainted(false);
		cpuBar.setBounds(10, 110, 300, 30);
		upPanel.add(cpuBar);
		
		JLabel cpuUsage = new JLabel("CPU 사용량");
		cpuUsage.setFont(font.deriveFont(12f));
		cpuUsage.setForeground(Color.GRAY);
		cpuUsage.setBounds(10,90,100,20); 
		upPanel.add(cpuUsage);
		
		JLabel cpuText = new JLabel("현재 PC의 CPU 사용량");
		cpuText.setFont(font.deriveFont(10f));
		cpuText.setForeground(Color.GRAY);
		cpuText.setBounds(200,100,120,10); 
		upPanel.add(cpuText);
		
		JProgressBar ramBar = new JProgressBar(0, 100);
		ramBar.setValue((int) Double.parseDouble(pc.getRam_data()));
		ramBar.setStringPainted(true);
		ramBar.setForeground(new Color(255,222,173));
		ramBar.setBackground(new Color(255,250,240));
		//ramBar.setBorder(BorderFactory.createLineBorder(new Color(211,211,211)));
		ramBar.setBorderPainted(false);
		ramBar.setBounds(10, 170, 300, 30);
		upPanel.add(ramBar);
		
		JLabel ramUsage = new JLabel("RAM 사용량");
		ramUsage.setFont(font.deriveFont(12f));
		ramUsage.setForeground(Color.GRAY);
		ramUsage.setBounds(10,150,100,20); 
		upPanel.add(ramUsage);
		
		JLabel ramText = new JLabel("현재 PC의 RAM 사용량");
		ramText.setFont(font.deriveFont(10f));
		ramText.setForeground(Color.GRAY);
		ramText.setBounds(200,160,120,10); 
		upPanel.add(ramText);
		
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
			}
		});
		okayButton.setBounds(130, 215, 60, 25);
		upPanel.add(okayButton);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.add(upPanel);
		
		userInfo.getContentPane().add(mainPanel);
		userInfo.setUndecorated(true);
		userInfo.setVisible(false);
		userInfo.setResizable(false);
		userInfo.setSize(320, 250);
		userInfo.setShape(new RoundRectangle2D.Double(0, 0, 320, 250, 10, 10));
	}
}
