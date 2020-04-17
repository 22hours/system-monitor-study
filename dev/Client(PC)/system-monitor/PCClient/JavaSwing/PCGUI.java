package PCClient.JavaSwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;

import PCClient.Http.PCPost;
import PCClient.Module.AlarmThread;
import PCClient.Module.ExtensionThread;
import PCClient.Module.GetLongPolling;
import PCClient.Module.GetLongPolling2;
import PCClient.Module.GetMACAddress;
import PCClient.Module.GetNowTime;
import PCClient.Module.IsDigit;
import PCClient.Module.PCExtension;
import PCClient.Module.PostGeneralPolling;
import PCClient.Module.PostLongPolling;
import PCClient.Module.PostLongPolling3;
import PCClient.Module.ShutdownHook;
import PCClient.Module.TimeDifference;
import PCClient.Module.TimerThread;
import PCModel.PC;
import sun.applet.Main;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import javax.swing.SwingConstants;

public class PCGUI {

	private JFrame frame;
	ExecutorService executorService = Executors.newFixedThreadPool(10);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PCGUI window = new PCGUI();
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
	public PCGUI() {
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		String[] extensionCombo = { "1시간", "2시간", "3시간", "4시간", "5시간" };
		final JComboBox combo = new JComboBox(extensionCombo);
		/*
		 * combo.setRenderer(new DefaultListCellRenderer() { 콤보 박스 select까지 색깔 바꾸기
		 * 
		 * @Override public void paint(Graphics g) { setBackground(Color.WHITE);
		 * setForeground(Color.BLACK); super.paint(g); } });
		 */
		combo.setBackground(Color.WHITE);
		combo.setForeground(Color.black);
		final String title = "시간 선택";
		final String[] options = { "선택", "취소" };
		GetMACAddress MAC = new GetMACAddress();
		String pid = MAC.getLocalMacAddress();
		final PC pc = new PC(pid);
		frame = new JFrame();
		UIManager UI = new UIManager();
		UI.put("OptionPane.background", Color.WHITE);
		UI.put("Panel.background", Color.WHITE);
		UI.put("Button.background", Color.DARK_GRAY);
		UI.put("Button.foreground", Color.WHITE);
		URL imageURL = Main.class.getResource("/img/tag-bg.jpg");
		URL hours22 = Main.class.getResource("/img/hours22.png");
		URL shutdownURL = Main.class.getResource("/img/stop22.png");
		URL shutdownPressedURL = Main.class.getResource("/img/stop11.png");
		URL iconURL = Main.class.getResource("/img/hours22.jpg");
		URL aboutURL = Main.class.getResource("/img/about-us2.png");
		frame.setTitle("System_monitor");
		ImageIcon ig = new ImageIcon(imageURL);
		ImageIcon ic = new ImageIcon(iconURL);
		frame.setIconImage(ic.getImage());
		final ImagePanel panel = new ImagePanel(ig.getImage());
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.CYAN);
		panel.setBounds(0, 0, 300, 110);
		frame.setSize(panel.getWidth(), panel.getHeight());
		frame.getContentPane().setLayout(null);
		panel.setLayout(null);
		frame.addWindowListener(getWindowAdapter());

		JLabel text = new JLabel("남은 시간 ");
		text.setFont(new Font("TimesRoman", Font.BOLD, 21));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setForeground(Color.GRAY);
		text.setSize(30, 30);
		text.setBounds(65, 15, 152, 50);
		panel.add(text);

		JLabel label = new JLabel("");
		label.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setSize(30, 30);
		label.setBounds(170, 15, 140, 50);
		panel.add(label);

		JButton shutdownButton = new JButton(new ImageIcon(shutdownURL));
		// shutdownButton.setBounds(86, 8, 50, 50);
		shutdownButton.setBounds(33, 17, 38, 38);
		shutdownButton.setBorderPainted(false);
		shutdownButton.setFocusPainted(false);
		shutdownButton.setContentAreaFilled(false);
		shutdownButton.setPressedIcon(new ImageIcon(shutdownPressedURL));
		shutdownButton.setToolTipText("사용종료");
		shutdownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon(Main.class.getResource("/img/hours22.png"));
				JLabel label = new JLabel("<html><meta charset=\"utf-8\">PC 종료하시겠습니까?</html>");
				label.setFont(new Font("TimesRoman", Font.BOLD, 15));
				label.setForeground(Color.GRAY);
				int input = JOptionPane.showConfirmDialog(frame, label, "PC 종료", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon);
				if (input == 0) {
					System.exit(0);
				} else {
					return;
				}
			}
		});
		panel.add(shutdownButton);
		/*
		 * JButton aboutusButton = new JButton(new ImageIcon(aboutURL));
		 * aboutusButton.setBounds(175, 2, 64, 64);
		 * aboutusButton.setBorderPainted(false); aboutusButton.setFocusPainted(false);
		 * aboutusButton.setContentAreaFilled(false);
		 * aboutusButton.setToolTipText("About Us"); aboutusButton.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent e) { try {
		 * System.out.println("Button Clicked!"); Desktop.getDesktop().browse(new
		 * URI("https://winterlood.github.io/sys-monitor-deploy/")); } catch
		 * (IOException | URISyntaxException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } } }); panel.add(aboutusButton);
		 */
		JButton extensionButton = new JButton("\uC5F0\uC7A5 \uC2E0\uCCAD");
		extensionButton.setBounds(20, 67, 88, 30);
		extensionButton.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		extensionButton.setBackground(Color.DARK_GRAY);
		extensionButton.setForeground(Color.WHITE);
		extensionButton.setToolTipText("연장신청");
		extensionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (true) {
					int selection = JOptionPane.showOptionDialog(frame, combo, title, JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					if (selection == 1 || selection == -1)
						break;
					String time = combo.getSelectedItem().toString().substring(0, 1);
					if (IsDigit.getInstance().isNumeric(time) && !time.equals("0")) {
						PCExtension.getInstance().Extension(pc, time);
						/*
						 * for(Thread t : Thread.getAllStackTraces().keySet()) {
						 * if(t.getName().equals("ExtensionThread")) { executorService.execute(new
						 * PostLongPolling(pc)); break; } else if(t.getName().equals("PostLongPolling"))
						 * { executorService.execute(new ExtensionThread(pc, time)); break; } }
						 */
						// Thread post = new PostLongPolling3(pc);
						// post.start();
						try {
							PCPost.getInstance().PostMethod(pc);
						} catch (URISyntaxException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JLabel label = new JLabel("<html><meta charset=\\\"utf-8\\\">" + time + "시간 연장 되었습니다.</html>");
						label.setFont(new Font("TimesRoman", Font.BOLD, 13));
						JOptionPane.showMessageDialog(null, label);
						break;
					}
				}
			}
		});
		panel.add(extensionButton);

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 1274, 842);
		mainPanel.setLayout(null);
		frame.getContentPane().add(panel);

		JButton remainTimeButton = new JButton("Dev");
		remainTimeButton.setToolTipText("개발자 정보");

		remainTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://damin8.github.io/"));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/*
				 * String nowTime = GetNowTime.getInstance().getNowTime();
				 * System.out.println(nowTime); String endTime = pc.getEnd_time();
				 * System.out.println(endTime); String remainTime =
				 * TimeDifference.getInstance().calc(nowTime, endTime);
				 * System.out.println(remainTime); if(remainTime==null) {
				 * JOptionPane.showMessageDialog(null, "잘못된 정보 수신!"); } else {
				 * JOptionPane.showMessageDialog(null, remainTime); }
				 */
			}
		});
		remainTimeButton.setForeground(Color.WHITE);
		remainTimeButton.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		remainTimeButton.setBackground(Color.DARK_GRAY);
		remainTimeButton.setBounds(210, 67, 88, 30);
		panel.add(remainTimeButton);

		JButton button = new JButton("\uB098\uC758 \uC815\uBCF4");
		button.setToolTipText("나의 정보");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon(Main.class.getResource("/img/hours22.png"));
				SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
				String startTime = null, endTime = null;
				try {
					Date originalDate = dayTime.parse(pc.getStart_time());
					long originalTime = originalDate.getTime();
					Date end_Time = dayTime.parse(pc.getEnd_time());
					long end_Time1 = end_Time.getTime();
					dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					startTime = dayTime.format(new Date(originalTime));
					endTime = dayTime.format(new Date(end_Time1));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				UIManager.put("ProgressBar.selectionBackground",Color.BLACK);
				JProgressBar progressBar = new JProgressBar(0, 100);
				progressBar.setValue((int) Double.parseDouble(pc.getCpu_data()));
				progressBar.setStringPainted(true);
				progressBar.setForeground(Color.LIGHT_GRAY);
				progressBar.setBackground(Color.WHITE);
				TitledBorder title = BorderFactory.createTitledBorder("CPU Usage");
				title.setTitleColor(Color.BLACK);
				title.setTitleFont(new Font("TimesRoman", Font.BOLD, 9));
				progressBar.setBorder(title);
				JProgressBar progressBar1 = new JProgressBar(0, 100);
				progressBar1.setValue((int) Double.parseDouble(pc.getRam_data()));
				progressBar1.setStringPainted(true);
				TitledBorder title1 = BorderFactory.createTitledBorder("RAM Usage");
				title1.setTitleColor(Color.BLACK);
				title1.setTitleFont(new Font("TimesRoman", Font.BOLD, 9));
				progressBar1.setBorder(title1);
				progressBar1.setForeground(Color.LIGHT_GRAY);
				progressBar1.setBackground(Color.WHITE);
				JLabel Start = new JLabel("StartTime ");
				Start.setForeground(Color.GRAY);
				Start.setFont(new Font("TimesRoman", Font.BOLD, 11));
				JLabel STime = new JLabel(": " + startTime);
				STime.setFont(new Font("TimesRoman", Font.BOLD, 12));
				JLabel End = new JLabel("EndTime ");
				End.setForeground(Color.GRAY);
				End.setFont(new Font("TimesRoman", Font.BOLD, 11));
				JLabel ETime = new JLabel("  : " + endTime);
				ETime.setFont(new Font("TimesRoman", Font.BOLD, 12));
				JPanel myPanel1 = new JPanel();
				myPanel1.setLayout(new BoxLayout(myPanel1, BoxLayout.X_AXIS));
				myPanel1.add(Start);
				myPanel1.add(STime);
				JPanel myPanel2 = new JPanel();
				myPanel2.setLayout(new BoxLayout(myPanel2, BoxLayout.X_AXIS));
				myPanel2.add(End);
				myPanel2.add(ETime);
				JPanel myPanel3 = new JPanel();
				myPanel3.setLayout(new BoxLayout(myPanel3, BoxLayout.Y_AXIS));
				myPanel3.add(myPanel1);
				myPanel3.add(myPanel2);
				myPanel3.add(progressBar);
				myPanel3.add(progressBar1);
				int result = JOptionPane.showConfirmDialog(frame, myPanel3, "ID : Damin", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon);
				// JOptionPane.showMessageDialog(frame, label, "사용자 정보",
				// JOptionPane.INFORMATION_MESSAGE, icon);
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		button.setBackground(Color.DARK_GRAY);
		button.setBounds(115, 67, 88, 30);
		panel.add(button);
		frame.setResizable(false);
		// frame.setLocationRelativeTo(null); // 가운데에서 Frame 출력하기
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = ((int) rect.getMaxX() - frame.getWidth());
		int y = 0;
		frame.setLocation(x, y);
		/*
		 * frame.setUndecorated(true); Color color =
		 * UIManager.getColor("activeCaptionBorder");
		 * frame.getRootPane().setBorder(BorderFactory.createLineBorder(color, 4));
		 */
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ShutdownHook shutdownhook = new ShutdownHook(pc);
		shutdownhook.AttachShutdownHook();
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setUndecorated(true); // 타이틀바 삭제
		frame.setBackground(new Color(255, 0, 0, 0)); // 투명
		frame.setVisible(true);
		executorService.execute(new TimerThread(label, pc));
		/*
		 * try { PCPost.getInstance().PostMethod(pc); executorService.execute(new
		 * GetLongPolling(pc)); executorService.execute(new PostGeneralPolling(pc)); }
		 * catch (URISyntaxException | IOException e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); } /*Thread post = new PostLongPolling3(pc);
		 * post.start(); for(Thread t : Thread.getAllStackTraces().keySet()) {
		 * if(t.getName().equals("Post")) { try { t.join(); } catch
		 * (InterruptedException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } } }
		 */
		// executorService.execute(new PostLongPolling(pc));
		// e
		// Thread get = new GetLongPolling2(pc);
		// get.start();
		// executorService.execute(new PostGeneralPolling(pc));
	}
}
