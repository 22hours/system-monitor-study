package PCClient.JavaSwing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame window = new TestFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public TestFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("System_Monitor");
		frame.setBounds(100, 100, 800, 600);
		frame.setLocationRelativeTo(null); // 가운데에 띄우기
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // 사이즈 변경 불가
		frame.getContentPane().setLayout(null);
		
		final JPanel firstPage = new JPanel();
		firstPage.setBounds(0, 0, 794, 565);
		frame.getContentPane().add(firstPage);
		firstPage.setLayout(null);
		
		JButton firstButton = new JButton("Go to last");

		firstButton.setBounds(409, 210, 248, 217);
		firstPage.add(firstButton);
		
		final JPanel lastPage = new JPanel();
		lastPage.setBounds(0, 0, 833, 565);
		frame.getContentPane().add(lastPage);
		lastPage.setLayout(null);
		
		JButton lastButton = new JButton("Go to next");
		lastButton.setBounds(100, 207, 248, 217);
		lastPage.add(lastButton);
		firstButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lastPage.setVisible(true);
				firstPage.setVisible(false);
			}
		});
		lastButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lastPage.setVisible(false);
				firstPage.setVisible(true);
			}
		});
	}
}
