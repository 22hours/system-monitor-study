package PCClient.JavaSwing;

import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.JTextField;
import javax.swing.UIManager;

import DataBase.CreateTable;
import DataBase.InsertData;
import DataBase.ReadData;
import PCClient.Http.PCPost;
import PCClient.Module.PCExtension;
import PCModel.ClassInfo;
import PCModel.PC;
import sun.applet.Main;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InitFrame {
	private JFrame init = new JFrame();
	private JPanel mainPanel = new JPanel();
	private static InitFrame instance = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitFrame window = new InitFrame();
					// --window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private InitFrame() {
		initialize();
	}

	public static InitFrame getInstance() {
		if (instance == null)
			instance = new InitFrame();
		return instance;
	}
	
	private void show() {
		URL dohyeonURL = Main.class.getResource("/img/MapoGoldenPier.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, dohyeonURL.openStream());
		} catch (FontFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		JPanel upPanel = new JPanel();
		upPanel.setBounds(0, 0, 240, 170); // x y xũ�� yũ��
		upPanel.setBackground(Color.WHITE);
		upPanel.setLayout(null);

		JLabel title = new JLabel("Initialization");
		title.setFont(font.deriveFont(15f));
		title.setBounds(80, 10, 100, 20);
		upPanel.add(title);

		JLabel classID = new JLabel("���ǽ� �� : ");
		classID.setFont(font.deriveFont(15f));
		classID.setBounds(25, 40, 100, 20);
		upPanel.add(classID);

		final JTextField classField = new JTextField(5);
		classField.setFont(font.deriveFont(15f));
		classField.setBounds(105, 40, 100, 20);
		upPanel.add(classField);

		JLabel posR = new JLabel("��ġ(��)   : ");
		posR.setFont(font.deriveFont(15f));
		posR.setBounds(25, 70, 100, 20);
		upPanel.add(posR);

		final JTextField posRField = new JTextField(5);
		posRField.setFont(font.deriveFont(15f));
		posRField.setBounds(105, 70, 100, 20);
		upPanel.add(posRField);

		JLabel posC = new JLabel("��ġ(��)   : ");
		posC.setFont(font.deriveFont(15f));
		posC.setBounds(25, 100, 100, 20);
		upPanel.add(posC);

		final JTextField posCField = new JTextField(5);
		posCField.setFont(font.deriveFont(15f));
		posCField.setBounds(105, 100, 100, 20);
		upPanel.add(posCField);

		final MyButton yesButton = new MyButton("Ȯ��");
		yesButton.setFont(font.deriveFont(12f));
		yesButton.setFocusPainted(false);
		yesButton.setBackground(new Color(192, 192, 192));
		yesButton.setForeground(Color.WHITE);
		yesButton.setBorder(null);
		yesButton.setHoverBackgroundColor(Color.BLACK);
		yesButton.setPressedBackgroundColor(Color.BLACK);
		yesButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String classID = classField.getText();
				String posR = posRField.getText();
				String posC = posCField.getText();
				if (classID.equals("") || posR.equals("") || posC.equals(""))
					MessageBox.getInstance().show();

				if (isInteger(posR) && isInteger(posC)) {
					int tempPosR = Integer.parseInt(posRField.getText());
					int tempPposC = Integer.parseInt(posCField.getText());
					boolean isit = InsertData.getInstance().insertData(classID, tempPosR, tempPposC);
					init.setVisible(false);
					PCUI.show();
					
				} else
					MessageBox.getInstance().show();

			}
		});
		yesButton.setBounds(40, 135, 60, 25);
		upPanel.add(yesButton);

		final MyButton noButton = new MyButton("���");
		noButton.setFont(font.deriveFont(12f));
		noButton.setFocusPainted(false);
		noButton.setBackground(new Color(192, 192, 192));
		noButton.setForeground(Color.WHITE);
		noButton.setBorder(null);
		noButton.setHoverBackgroundColor(Color.BLACK);
		noButton.setPressedBackgroundColor(Color.BLACK);
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		noButton.setBounds(140, 135, 60, 25);
		upPanel.add(noButton);

		mainPanel.setLayout(null);
		mainPanel.add(upPanel);

		init.getContentPane().add(mainPanel);
		init.setUndecorated(true);
		init.setVisible(true);
		init.setResizable(false);
		init.setSize(240, 170);
		init.setShape(new RoundRectangle2D.Double(0, 0, 240, 170, 10, 10));
		init.setLocationRelativeTo(null);
	}
	
	private void initialize() {
		boolean createTable = CreateTable.getInstance().createTable();
		if (!createTable) {
			System.out.println("���̺� ���� ����!");
			System.exit(0);
		}
		
		ClassInfo classInfo = ReadData.getInstance().readData();

		if (classInfo == null)  // ������ ���ٸ�
			show();
		
		else 
			PCUI.show();
		
		
	}
	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
