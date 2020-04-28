package PCClient.JavaSwing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import PCClient.Http.PCPost;
import PCClient.Module.PCExtension;
import PCModel.PC;
import sun.applet.Main;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExtensionFrame {
	JFrame userInfo = new JFrame();
	private static ExtensionFrame instance = null;
	private PC pc = null;
	private Boolean isit = false;
	private ExtensionFrame() {
	}
	
	public static ExtensionFrame getInstance() {
		if(instance==null)
			instance = new ExtensionFrame();
		return instance;
	}
	
	public synchronized void show(JFrame jframe, PC pc) {
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
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, dohyeonURL.openStream());
		} catch (FontFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		JPanel upPanel = new JPanel();
		upPanel.setBounds(0, 0, 240, 160); // x y x크기 y크기
		upPanel.setBackground(Color.WHITE);
		upPanel.setLayout(null);
		
		final MyButton onehourExtension = new MyButton("1시간 연장하기");
		onehourExtension.setFont(font.deriveFont(12f));
		onehourExtension.setFocusPainted(false);
		onehourExtension.setBackground(new Color(192, 192, 192));
		onehourExtension.setForeground(Color.WHITE);
		onehourExtension.setBorder(null);
		onehourExtension.setHoverBackgroundColor(Color.BLACK);
		onehourExtension.setPressedBackgroundColor(Color.BLACK);
		onehourExtension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPost("1");
				userInfo.setVisible(false);
			}
		});
		onehourExtension.setBounds(10, 10, 100, 25);
		upPanel.add(onehourExtension);
		
		final MyButton twohourExtension = new MyButton("2시간 연장하기");
		twohourExtension.setFont(font.deriveFont(12f));
		twohourExtension.setFocusPainted(false);
		twohourExtension.setBackground(new Color(192, 192, 192));
		twohourExtension.setForeground(Color.WHITE);
		twohourExtension.setBorder(null);
		twohourExtension.setHoverBackgroundColor(Color.BLACK);
		twohourExtension.setPressedBackgroundColor(Color.BLACK);
		twohourExtension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPost("2");
				userInfo.setVisible(false);
			}
		});
		twohourExtension.setBounds(130, 10, 100, 25);
		upPanel.add(twohourExtension);
		
		final MyButton threehourExtension = new MyButton("3시간 연장하기");
		threehourExtension.setFont(font.deriveFont(12f));
		threehourExtension.setFocusPainted(false);
		threehourExtension.setBackground(new Color(192, 192, 192));
		threehourExtension.setForeground(Color.WHITE);
		threehourExtension.setBorder(null);
		threehourExtension.setHoverBackgroundColor(Color.BLACK);
		threehourExtension.setPressedBackgroundColor(Color.BLACK);
		threehourExtension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPost("3");
				userInfo.setVisible(false);
			}
		});
		threehourExtension.setBounds(10, 50, 100, 25);
		upPanel.add(threehourExtension);
		
		final MyButton fourhourExtension = new MyButton("4시간 연장하기");
		fourhourExtension.setFont(font.deriveFont(12f));
		fourhourExtension.setFocusPainted(false);
		fourhourExtension.setBackground(new Color(192, 192, 192));
		fourhourExtension.setForeground(Color.WHITE);
		fourhourExtension.setBorder(null);
		fourhourExtension.setHoverBackgroundColor(Color.BLACK);
		fourhourExtension.setPressedBackgroundColor(Color.BLACK);
		fourhourExtension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPost("4");
				userInfo.setVisible(false);
			}
		});
		fourhourExtension.setBounds(130, 50, 100, 25);
		upPanel.add(fourhourExtension);
		
		final MyButton fivehourExtension = new MyButton("5시간 연장하기");
		fivehourExtension.setFont(font.deriveFont(12f));
		fivehourExtension.setFocusPainted(false);
		fivehourExtension.setBackground(new Color(192, 192, 192));
		fivehourExtension.setForeground(Color.WHITE);
		fivehourExtension.setBorder(null);
		fivehourExtension.setHoverBackgroundColor(Color.BLACK);
		fivehourExtension.setPressedBackgroundColor(Color.BLACK);
		fivehourExtension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPost("5");
				userInfo.setVisible(false);
			}
		});
		fivehourExtension.setBounds(10, 90, 100, 25);
		upPanel.add(fivehourExtension);
		
		final MyButton sixhourExtension = new MyButton("6시간 연장하기");
		sixhourExtension.setFont(font.deriveFont(12f));
		sixhourExtension.setFocusPainted(false);
		sixhourExtension.setBackground(new Color(192, 192, 192));
		sixhourExtension.setForeground(Color.WHITE);
		sixhourExtension.setBorder(null);
		sixhourExtension.setHoverBackgroundColor(Color.BLACK);
		sixhourExtension.setPressedBackgroundColor(Color.BLACK);
		sixhourExtension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doPost("6");
				userInfo.setVisible(false);
			}
		});
		sixhourExtension.setBounds(130, 90, 100, 25);
		upPanel.add(sixhourExtension);

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
		noButton.setBounds(95, 125, 60, 25);
		upPanel.add(noButton);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.add(upPanel);
		
		userInfo.getContentPane().add(mainPanel);
		userInfo.setUndecorated(true);
		userInfo.setVisible(false);
		userInfo.setResizable(false);
		userInfo.setSize(240, 160);
		userInfo.setShape(new RoundRectangle2D.Double(0, 0, 240, 160, 10, 10));
	}
	private void doPost(String time) {
		PCExtension.getInstance().Extension(pc, time);
		try {
			PCPost.getInstance().PostMethod(pc);
		} catch (URISyntaxException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
