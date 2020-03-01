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
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
		frame = new JFrame();
		ImagePanel panel = new ImagePanel(new ImageIcon("F:/eclipse-jee-2019-12-R-win32-x86_64/eclipse-workspace/System-Monitor(PC Client)/img/tag-bg.jpg").getImage());
		panel.setBounds(0, 0, 1274, 854);
		frame.setSize(panel.getWidth(),panel.getHeight());
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
		
		JButton loginbutton = new JButton("Log In");
		loginbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("I'm clicked");
			}
		});
		loginbutton.setBounds(559, 386, 179, 43);
		panel.add(loginbutton);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 1274, 842);
		mainPanel.setLayout(null);
		frame.getContentPane().add(panel);
		frame.setResizable(false);	
		frame.setLocationRelativeTo(null); // 가운데에서 Frame 출력하기
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
