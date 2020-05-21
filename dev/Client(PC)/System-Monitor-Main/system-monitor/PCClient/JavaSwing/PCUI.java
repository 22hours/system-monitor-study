package PCClient.JavaSwing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import DataBase.CreateTable;
import DataBase.ReadData;
import PCClient.Http.PCPost;
import PCClient.Module.GetLongPolling;
import PCClient.Module.GetMACAddress;
import PCClient.Module.PostGeneralPolling;
import PCClient.Module.ShutdownHook;
import PCClient.Module.TimerThread;
import PCModel.ClassInfo;
import PCModel.PC;
import sun.applet.Main;

import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class PCUI {
	ExecutorService executorService = Executors.newFixedThreadPool(10);
	private static PCUI instance = null;
	private JFrame frame;
	private PCUI() {
		initialize();
	}
	public static void show() {
		if(instance==null)
			instance = new PCUI();
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
		GetMACAddress MAC = new GetMACAddress();
		String pid = MAC.getLocalMacAddress();
		final PC pc = new PC(pid);
		
		ClassInfo classInfo = ReadData.getInstance().readData();
		System.out.println("Read Data = " + classInfo.toString());

		String dbname = classInfo.getName();
		String dbclassID = classInfo.getClassID();
		int dbposR = classInfo.getPosR();
		int dbposC = classInfo.getPosC();
		

		pc.setName(dbname);
		pc.setClass_id(dbclassID);
		pc.setPosR(dbposR);
		pc.setPosC(dbposC);
		pc.setId(dbname);
		
		URL shutdownURL = getClass().getClassLoader().getResource("stop22.png");
		URL shutdownPressedURL = getClass().getClassLoader().getResource("stop11.png");
		URL homeURL = getClass().getClassLoader().getResource("Home.PNG");
		URL dohyeonURL = getClass().getClassLoader().getResource("MapoGoldenPier.ttf");
		URL mapoURL = getClass().getClassLoader().getResource("MapoGoldenPier.ttf");
		URL iconURL = getClass().getClassLoader().getResource("SystemMonitor.png");

		Font font = null;
		Font mapofont = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, dohyeonURL.openStream());
			mapofont = Font.createFont(Font.TRUETYPE_FONT, mapoURL.openStream());
		} catch (FontFormatException | IOException e2) {
			e2.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		ge.registerFont(mapofont);
		font = font.deriveFont(15f);
		mapofont = mapofont.deriveFont(15f);

		frame = new JFrame();
		ImageIcon ic = new ImageIcon(iconURL);
		frame.setIconImage(ic.getImage());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JPanel upPanel = new JPanel();
		upPanel.setBorder(null);
		upPanel.setBounds(0, 0, 320, 100);
		upPanel.setBackground(SystemColor.control);
		upPanel.setLayout(null);

		JLabel label = new JLabel("11 : 11");
		label.setFont(font.deriveFont(40f));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setSize(30, 30);
		label.setForeground(Color.BLACK);
		label.setBounds(0, 13, 154, 52);
		upPanel.add(label);

		JLabel text = new JLabel("남은 시간 ");
		text.setFont(mapofont);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setForeground(Color.GRAY);
		text.setSize(30, 30);
		text.setBounds(148, 34, 74, 30);
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
				ShutdownFrame.getInstance().show(frame);
			}
		});
		upPanel.add(shutdownButton);

		JLabel classID = new JLabel(pc.getName());
		classID.setFont(mapofont);
		classID.setBounds(35, 75, 100, 20);
		upPanel.add(classID);

		JLabel version = new JLabel("현재 버전 : 1.0.13");
		version.setFont(mapofont.deriveFont(13f));
		version.setForeground(Color.GRAY);
		version.setHorizontalAlignment(JLabel.CENTER);
		version.setBounds(190, 76, 123, 18);
		upPanel.add(version);

		JLabel home = new JLabel(new ImageIcon(homeURL));
		home.setBounds(10, 76, 26, 18);
		upPanel.add(home);

		JPanel downPanel = new JPanel();
		downPanel.setBorder(null);
		downPanel.setBounds(0, 100, 320, 50); // x y x크기 y크기
		downPanel.setBackground(Color.WHITE);
		downPanel.setLayout(null);

		final MyButton extensionButton = new MyButton("연장 신청");
		extensionButton.setBackground(new Color(192, 192, 192));
		extensionButton.setForeground(Color.WHITE);
		extensionButton.setHoverBackgroundColor(Color.BLACK);
		extensionButton.setPressedBackgroundColor(Color.BLACK);
		extensionButton.setBounds(14, 12, 90, 30);
		extensionButton.setFont(mapofont);
		extensionButton.setToolTipText("연장신청");
		extensionButton.setBorder(null);
		extensionButton.setFocusPainted(false);
		extensionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExtensionFrame.getInstance().show(frame, pc);
			}
		});
		downPanel.add(extensionButton);

		final MyButton userData = new MyButton("나의 정보");
		userData.setBackground(new Color(192, 192, 192));
		userData.setForeground(Color.WHITE);
		userData.setFont(mapofont);
		userData.setBounds(114, 12, 90, 30);
		userData.setBorder(null);
		userData.setToolTipText("나의 정보");
		userData.setHoverBackgroundColor(Color.BLACK);
		userData.setPressedBackgroundColor(Color.BLACK);
		userData.setFocusPainted(false);
		userData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserInfo.getInstance().show(frame, pc);
			}
		});
		downPanel.add(userData);

		final MyButton dev = new MyButton("개발자");
		// dev.setBackground(new Color(0, 206, 209));
		dev.setBackground(new Color(192, 192, 192));
		dev.setForeground(Color.WHITE);
		dev.setBorder(null);
		dev.setFont(mapofont);
		dev.setFocusPainted(false);
		dev.setBounds(214, 12, 90, 30);
		dev.setToolTipText("개발자 홈페이지");
		dev.setHoverBackgroundColor(Color.BLACK);
		dev.setPressedBackgroundColor(Color.BLACK);
		dev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.22hours.online/"));
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

		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = ((int) rect.getMaxX() - frame.getWidth());
		int y = 0;

		frame.setLocation(x, y);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		executorService.execute(new TimerThread(label, pc));
		ShutdownHook shutdownhook = new ShutdownHook(pc);
		shutdownhook.AttachShutdownHook();
		
		/*try {
			PCPost.getInstance().PostMethod(pc);
			executorService.execute(new GetLongPolling(pc));
			executorService.execute(new PostGeneralPolling(pc));
		} catch (URISyntaxException | IOException e1) { // TODO Auto-generated catch
			e1.printStackTrace();
		}*/

	}
}
