package PCClient.JavaSwing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import PCClient.Module.ExtensionThread;
import PCClient.Module.GetLongPolling;
import PCClient.Module.GetMACAddress;
import PCClient.Module.GetNowTime;
import PCClient.Module.IsDigit;
import PCClient.Module.PCExtension;
import PCClient.Module.PostGeneralPolling;
import PCClient.Module.PostLongPolling;
import PCClient.Module.TimeDifference;
import PCModel.PC;
import sun.applet.Main;
import test.shutdownhook;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		GetMACAddress MAC = new GetMACAddress();
		String pid = MAC.getLocalMacAddress();
		final PC pc = new PC(pid);
		frame = new JFrame();
		URL imageURL = Main.class.getResource("/img/tag-bg.jpg");
		URL iconURL = Main.class.getResource("/img/hours22.jpg");
		frame.setTitle("System_monitor");
		ImageIcon ig = new ImageIcon(imageURL);
		ImageIcon ic = new ImageIcon(iconURL);
		frame.setIconImage(ic.getImage());
		ImagePanel panel = new ImagePanel(ig.getImage());
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.CYAN);
		panel.setBounds(0, 0, 450, 250);
		frame.setSize(panel.getWidth(), panel.getHeight());
		frame.getContentPane().setLayout(null);
		panel.setLayout(null);

		JButton extensionButton = new JButton("\uC5F0\uC7A5 \uC2E0\uCCAD");
		extensionButton.setFont(new Font("Gulim", Font.PLAIN, 12));
		extensionButton.setBackground(Color.DARK_GRAY);
		extensionButton.setForeground(Color.WHITE);
		extensionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (true) {
					String time = JOptionPane.showInputDialog("늘릴 시간을 입력하세요.");
					if (IsDigit.getInstance().isNumeric(time) && !time.equals("0")) {
						PCExtension.getInstance().Extension(pc, time);
						executorService.execute(new ExtensionThread(pc, time));
						JOptionPane.showMessageDialog(null, time + "시간 연장 되었습니다.");
						break;
					}
					else if(time.equals("0")) {
						JOptionPane.showMessageDialog(null, "다시 입력해주세요.");
					}
					else {
						JOptionPane.showMessageDialog(null, "숫자만 입력해주세요.");
					}
				}
			}
		});	
		extensionButton.setBounds(20, 32, 88, 30);
		panel.add(extensionButton);

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 1274, 842);
		mainPanel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JButton remainTimeButton = new JButton("\uB0A8\uC740 \uC2DC\uAC04");
		remainTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nowTime = GetNowTime.getInstance().getNowTime();
				System.out.println(nowTime);
				String endTime = pc.getEnd_time();
				System.out.println(endTime);
				String remainTime = TimeDifference.getInstance().calc(nowTime, endTime);
				System.out.println(remainTime);
				if(remainTime==null) {
					JOptionPane.showMessageDialog(null, "잘못된 정보 수신!");
				}
				else {
					JOptionPane.showMessageDialog(null, remainTime);
				}
			}
		});
		remainTimeButton.setForeground(Color.WHITE);
		remainTimeButton.setFont(new Font("Gulim", Font.PLAIN, 12));
		remainTimeButton.setBackground(Color.DARK_GRAY);
		remainTimeButton.setBounds(210, 32, 88, 30);
		panel.add(remainTimeButton);
		
		JButton button = new JButton("\uB098\uC758 \uC815\uBCF4");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "ID : damin\nStartTime : ";
				msg += pc.getStart_time();
				msg += "\nEndTime : ";
				msg += pc.getEnd_time();
				msg += "\nCPU Usage : ";
				msg += pc.getCpu_data();
				msg += "%\nRam Usage : ";
				msg += pc.getRam_data();
				msg += "%";
				JOptionPane.showMessageDialog(null, msg);
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Gulim", Font.PLAIN, 12));
		button.setBackground(Color.DARK_GRAY);
		button.setBounds(115, 32, 88, 30);
		panel.add(button);
		frame.setResizable(false);
		//frame.setLocationRelativeTo(null); // 가운데에서 Frame 출력하기
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	    int x = ((int) rect.getMaxX() - frame.getWidth()) /2;
	    int y = 0;
		frame.setLocation(x, y);
		/*frame.setUndecorated(true);
		Color color = UIManager.getColor("activeCaptionBorder");
		frame.getRootPane().setBorder(BorderFactory.createLineBorder(color, 4));*/
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new shutdownhook());
		try {
			executorService.execute(new PostLongPolling(pc));
			Thread.sleep(3000);
			executorService.execute(new GetLongPolling(pc));
			executorService.execute(new PostGeneralPolling(pc));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
