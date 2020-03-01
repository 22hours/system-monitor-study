package PCClient.JavaSwing;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import PCClient.Http.PCPost;
import PCClient.Module.GetLongPolling;
import PCClient.Module.GetMACAddress;
import PCClient.Module.GetNowTime;
import PCClient.Module.IsDigit;
import PCClient.Module.PostLongPolling;
import PCClient.Module.TimeDifference;
import PCModel.PC;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class PCGUI {

	private JFrame frame;
	private JTextField id;
	private JPasswordField password;

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
		frame.setTitle("22hours_System-Monitor");
		ImagePanel panel = new ImagePanel(new ImageIcon(
				"F:/eclipse-jee-2019-12-R-win32-x86_64/eclipse-workspace/System-Monitor(PC Client)/img/tag-bg.jpg")
						.getImage());
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.CYAN);
		panel.setBounds(0, 0, 1274, 854);
		frame.setSize(panel.getWidth(), panel.getHeight());
		frame.getContentPane().setLayout(null);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Log In");
		lblNewLabel.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 30));
		lblNewLabel.setBounds(584, 199, 118, 63);
		panel.add(lblNewLabel);

		id = new JTextField();
		id.setBounds(559, 274, 179, 43);
		panel.add(id);
		id.setColumns(10);

		password = new JPasswordField();
		password.setBounds(559, 331, 179, 43);
		panel.add(password);

		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(495, 274, 50, 43);
		panel.add(lblNewLabel_1);

		JLabel lblPassword = new JLabel("PW");
		lblPassword.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 30));
		lblPassword.setBounds(495, 331, 50, 43);
		panel.add(lblPassword);

		JButton extensionButton = new JButton("\uC5F0\uC7A5 \uC2E0\uCCAD");
		extensionButton.setFont(new Font("Gulim", Font.PLAIN, 15));
		extensionButton.setBackground(Color.GRAY);
		extensionButton.setForeground(Color.WHITE);
		extensionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (true) {
					String time = JOptionPane.showInputDialog("늘릴 시간을 입력하세요.");
					if (IsDigit.getInstance().isNumeric(time) && !time.equals("0")) {
						/*try {
							PCPost.getInstance().Extension(pc, time);
						} catch (URISyntaxException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
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
		extensionButton.setBounds(525, 386, 109, 43);
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
		remainTimeButton.setFont(new Font("Gulim", Font.PLAIN, 15));
		remainTimeButton.setBackground(Color.GRAY);
		remainTimeButton.setBounds(654, 386, 109, 43);
		panel.add(remainTimeButton);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // 가운데에서 Frame 출력하기
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			Thread PostGeneralThread = new PostLongPolling(pc);
			Thread GetLongThread = new GetLongPolling(pc);
			PostGeneralThread.start();
			Thread.sleep(15000);
			GetLongThread.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
