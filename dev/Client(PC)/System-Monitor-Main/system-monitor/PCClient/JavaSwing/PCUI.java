package PCClient.JavaSwing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import PCClient.Module.GetMACAddress;
import PCClient.Module.TimerThread;
import PCModel.PC;
import sun.applet.Main;

import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class PCUI {
	private JFrame frame;
	ExecutorService executorService = Executors.newFixedThreadPool(10);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PCUI window = new PCUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PCUI() {
		initialize();
	}

	private WindowAdapter getWindowAdapter() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {// overrode to show message
				super.windowClosing(we);
			}

			@Override
			public void windowIconified(WindowEvent we) {
				frame.setState(JFrame.NORMAL);
			}
		};
	}

	private void initialize() {
		// GetMACAddress MAC = new GetMACAddress();
		// String pid = MAC.getLocalMacAddress();
		// final PC pc = new PC("");
		URL shutdownURL = Main.class.getResource("/img/stop22.png");
		URL shutdownPressedURL = Main.class.getResource("/img/stop11.png");
		URL homeURL = Main.class.getResource("/img/Home.PNG");

		frame = new JFrame();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JPanel upPanel = new JPanel();
		upPanel.setBounds(0, 0, 320, 100);
		upPanel.setBackground(SystemColor.control);
		upPanel.setLayout(null);

		JLabel label = new JLabel("12 : 12");
		label.setFont(new Font("TimesRoman", Font.PLAIN, 45));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setSize(30, 30);
		label.setForeground(Color.BLACK);
		label.setBounds(0, 13, 154, 52);
		upPanel.add(label);

		JLabel text = new JLabel("남은 시간 ");
		text.setFont(new Font("TimesRoman", Font.BOLD, 15));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setForeground(Color.GRAY);
		text.setSize(30, 30);
		text.setBounds(153, 34, 83, 30);
		upPanel.add(text);

		JButton shutdownButton = new JButton(new ImageIcon(shutdownURL));
		shutdownButton.setBounds(236, 20, 50, 44);
		shutdownButton.setToolTipText("사용종료");
		shutdownButton.setBorderPainted(false);
		shutdownButton.setFocusPainted(false);
		shutdownButton.setContentAreaFilled(false);
		shutdownButton.setPressedIcon(new ImageIcon(shutdownPressedURL));
		shutdownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel label = new JLabel("<html><meta charset=\"utf-8\">PC 종료하시겠습니까?</html>");
				label.setFont(new Font("TimesRoman", Font.BOLD, 15));
				label.setForeground(Color.GRAY);
				int input = JOptionPane.showConfirmDialog(frame, label, " PC 종료", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (input == 0) {
					System.exit(0);
				} else {
					return;
				}
			}
		});
		upPanel.add(shutdownButton);

		JLabel classID = new JLabel("D404");
		classID.setFont(new Font("TimesRoman", Font.BOLD, 15));
		classID.setBounds(35, 72, 40, 18);
		upPanel.add(classID);

		JLabel version = new JLabel("현재 버전 : 1.0.13");
		version.setFont(new Font("TimesRoman", Font.BOLD, 13));
		version.setHorizontalAlignment(JLabel.CENTER);
		version.setForeground(Color.DARK_GRAY);
		version.setBounds(177, 76, 123, 18);
		upPanel.add(version);

		JLabel home = new JLabel(new ImageIcon(homeURL));
		home.setBounds(10, 70, 26, 18);
		upPanel.add(home);

		JPanel downPanel = new JPanel();
		downPanel.setBounds(0, 100, 320, 50); // x y x크기 y크기
		downPanel.setBackground(Color.WHITE);
		downPanel.setLayout(null);

		JButton extensionButton = new JButton("연장 신청");
		extensionButton.setBackground(new Color(106, 90, 205));
		extensionButton.setForeground(Color.WHITE);
		extensionButton.setBounds(14, 12, 90, 30);
		extensionButton.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		extensionButton.setToolTipText("연장신청");
		extensionButton.setBorder(null);
		downPanel.add(extensionButton);

		JButton userData = new JButton("나의 정보");
		userData.setBackground(new Color(106, 90, 205));
		userData.setForeground(Color.WHITE);
		userData.setFont(new Font("Dialog", Font.PLAIN, 13));
		userData.setBounds(114, 12, 90, 30);
		userData.setBorder(null);
		userData.setToolTipText("나의 정보");
		downPanel.add(userData);

		JButton dev = new JButton("개발자");
		//dev.setBackground(new Color(0, 206, 209));
		dev.setBackground(new Color(106, 90, 205));
		dev.setForeground(Color.WHITE);
		dev.setBorder(null);
		dev.setFont(new Font("Dialog", Font.PLAIN, 13));
		dev.setBounds(214, 12, 90, 30);
		dev.setToolTipText("개발자 홈페이지");
		dev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://damin8.github.io/"));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		downPanel.add(dev);

		mainPanel.setLayout(null);
		mainPanel.add(upPanel);
		mainPanel.add(downPanel);

		frame.getContentPane().add(mainPanel);
		frame.addWindowListener(getWindowAdapter());
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setSize(320, 150);
		frame.setShape(new RoundRectangle2D.Double(0, 0, 320, 150, 10, 10));
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = ((int) rect.getMaxX() - frame.getWidth());
		int y = 0;
		frame.setLocation(x, y);
		// executorService.execute(new TimerThread(label, pc));
		// frame.setShape(new RoundRectangle2D.Double(0, 0, 300, 150, 10, 10));
		/*
		 * JPanel panel = new JPanel(); panel.setBackground(Color.PINK);
		 * panel.setBounds(0, 0, 100, 76); frame.setSize(500, 251);
		 * frame.getContentPane().add(panel); panel.setLayout(null);
		 * frame.addWindowListener(getWindowAdapter()); frame.setResizable(false);
		 * frame.setForeground(Color.pink); GraphicsEnvironment ge =
		 * GraphicsEnvironment.getLocalGraphicsEnvironment(); GraphicsDevice
		 * defaultScreen = ge.getDefaultScreenDevice(); Rectangle rect =
		 * defaultScreen.getDefaultConfiguration().getBounds(); int x = ((int)
		 * rect.getMaxX() - frame.getWidth()); int y = 0; frame.setLocation(x, y);
		 */
		/*
		 * frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		 * frame.setUndecorated(true); // 타이틀바 삭제 frame.setBackground(Color.BLACK); //
		 * 투명 frame.setVisible(true); frame.setShape(new RoundRectangle2D.Double(0, 0,
		 * panel.getWidth(), panel.getHeight(), 10, 10));
		 */
	}
}
