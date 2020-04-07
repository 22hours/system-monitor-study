package PCClient.JavaSwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import PCClient.Module.ExtensionThread;
import PCClient.Module.GetLongPolling;
import PCClient.Module.GetMACAddress;
import PCClient.Module.GetNowTime;
import PCClient.Module.IsDigit;
import PCClient.Module.PCExtension;
import PCClient.Module.PostGeneralPolling;
import PCClient.Module.PostLongPolling;
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
            public void windowClosing(WindowEvent we) {//overrode to show message
                super.windowClosing(we);

                JOptionPane.showMessageDialog(frame, "Cant Exit");
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
		String[] extensionCombo = {"1시간","2시간","3시간","4시간","5시간"};
		final JComboBox combo = new JComboBox(extensionCombo);
		final String title = "시간 선택";
		final String[] options = {"선택","취소"};
		GetMACAddress MAC = new GetMACAddress();
		String pid = MAC.getLocalMacAddress();
		final PC pc = new PC(pid);
		frame = new JFrame();
		URL imageURL = Main.class.getResource("/img/tag-bg.jpg");
		URL hours22 = Main.class.getResource("/img/hours22.png");
		URL shutdownURL = Main.class.getResource("/img/stop2.png");
		URL shutdownPressedURL = Main.class.getResource("/img/stop1.png");
		URL iconURL = Main.class.getResource("/img/hours22.jpg");
		URL aboutURL = Main.class.getResource("/img/about-us2.png");
		frame.setTitle("System_monitor");
		ImageIcon ig = new ImageIcon(imageURL);
		ImageIcon ic = new ImageIcon(iconURL);
		frame.setIconImage(ic.getImage());
		ImagePanel panel = new ImagePanel(ig.getImage());
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.CYAN);
		panel.setBounds(0, 0, 300, 100);
		frame.setSize(panel.getWidth(), panel.getHeight());
		frame.getContentPane().setLayout(null);
		panel.setLayout(null);
        frame.addWindowListener(getWindowAdapter());
		
		JLabel text = new JLabel("남은 시간 :");
		text.setFont(new Font("TimesRoman", Font.BOLD, 25));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setSize(30,30);
		text.setBounds(60, 10, 140, 50);
		panel.add(text);
		
		JLabel label = new JLabel("");
		label.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setSize(30,30);
		label.setBounds(170, 10, 140, 50);
		panel.add(label);
		
		JButton shutdownButton = new JButton(new ImageIcon(shutdownURL));
		//shutdownButton.setBounds(86, 8, 50, 50);
		shutdownButton.setBounds(20, 8, 50, 50);
		shutdownButton.setBorderPainted(false);
		shutdownButton.setFocusPainted(false);
		shutdownButton.setContentAreaFilled(false);
		shutdownButton.setPressedIcon(new ImageIcon(shutdownPressedURL));
		shutdownButton.setToolTipText("사용종료");
		shutdownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon(Main.class.getResource("/img/hours22.png"));
		        int input = JOptionPane.showConfirmDialog(frame, 
		                "PC 종료하시겠습니까?", "PC 종료", 
		                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
		        if(input==0) {
		        	//shutdown 추가 해야 할 듯
					System.exit(0);
		        }
		        else {
		        	return; 
		        }
			}
		});
		panel.add(shutdownButton);
		/*JButton aboutusButton = new JButton(new ImageIcon(aboutURL));
		aboutusButton.setBounds(175, 2, 64, 64);
		aboutusButton.setBorderPainted(false);
		aboutusButton.setFocusPainted(false);
		aboutusButton.setContentAreaFilled(false);
		aboutusButton.setToolTipText("About Us");
		aboutusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Button Clicked!");
					Desktop.getDesktop().browse(new URI("https://winterlood.github.io/sys-monitor-deploy/"));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(aboutusButton);*/
		JButton extensionButton = new JButton("\uC5F0\uC7A5 \uC2E0\uCCAD");
		extensionButton.setFont(new Font("Gulim", Font.PLAIN, 12));
		extensionButton.setBackground(Color.DARK_GRAY);
		extensionButton.setForeground(Color.WHITE);
		extensionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (true) {
					int selection = JOptionPane.showOptionDialog(null, combo, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,options,options[0]);
					if(selection==1 || selection==-1) break;
					String time = combo.getSelectedItem().toString().substring(0,1);
					if (IsDigit.getInstance().isNumeric(time) && !time.equals("0")) {
						PCExtension.getInstance().Extension(pc, time);
						/*for(Thread t : Thread.getAllStackTraces().keySet()) {
							if(t.getName().equals("ExtensionThread")) {
								executorService.execute(new PostLongPolling(pc));
								break;
							}
							else if(t.getName().equals("PostLongPolling")) {
								executorService.execute(new ExtensionThread(pc, time));
								break;
							}
						}*/
						executorService.execute(new PostLongPolling(pc));
						JOptionPane.showMessageDialog(null, time + "시간 연장 되었습니다.");
						break;
					}
				}
			}
		});	
		extensionButton.setBounds(20, 62, 88, 30);
		panel.add(extensionButton);

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 1274, 842);
		mainPanel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JButton remainTimeButton = new JButton("Dev");
		remainTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // 개발자 정보로 바꾸기
				/*String nowTime = GetNowTime.getInstance().getNowTime();
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
				}*/
			}
		});
		remainTimeButton.setForeground(Color.WHITE);
		remainTimeButton.setFont(new Font("Gulim", Font.PLAIN, 12));
		remainTimeButton.setBackground(Color.DARK_GRAY);
		remainTimeButton.setBounds(210, 62, 88, 30);
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
		button.setBounds(115, 62, 88, 30);
		panel.add(button);
		frame.setResizable(false);
		//frame.setLocationRelativeTo(null); // 가운데에서 Frame 출력하기
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	    int x = ((int) rect.getMaxX() - frame.getWidth());
	    int y = 0;
		frame.setLocation(x, y);
		/*frame.setUndecorated(true);
		Color color = UIManager.getColor("activeCaptionBorder");
		frame.getRootPane().setBorder(BorderFactory.createLineBorder(color, 4));*/
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ShutdownHook shutdownhook = new ShutdownHook(pc);
		shutdownhook.AttachShutdownHook();
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setUndecorated(true); // 타이틀바 삭제
		frame.setBackground(new Color(255, 0, 0, 0)); // 투명
		frame.setVisible(true);
		executorService.execute(new TimerThread(label,pc));
		
		/*try {
			executorService.execute(new PostLongPolling(pc));
			Thread.sleep(3000);
			executorService.execute(new GetLongPolling(pc));
			executorService.execute(new PostGeneralPolling(pc));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
