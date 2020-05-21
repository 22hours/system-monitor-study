package PCClient.JavaSwing;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
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
import javax.swing.border.EmptyBorder;

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
		URL iconURL = getClass().getClassLoader().getResource("SystemMonitor.png");
		ImageIcon ic = new ImageIcon(iconURL);
		
		URL dohyeonURL = getClass().getClassLoader().getResource("MapoGoldenPier.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, dohyeonURL.openStream());
		} catch (FontFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		
		JPanel upPanel = new JPanel();
		upPanel.setBounds(0, 0, 240, 170); // x y x크기 y크기
		upPanel.setBackground(Color.WHITE);
		upPanel.setLayout(null);

		JLabel title = new JLabel("Initialization");
		title.setFont(font.deriveFont(15f));
		title.setBounds(80, 10, 100, 20);
		upPanel.add(title);

		JLabel classID = new JLabel("강의실 명 : ");
		classID.setFont(font.deriveFont(15f));
		classID.setBounds(25, 40, 100, 20);
		upPanel.add(classID);

		final JTextField classField = new JTextField(5);
		classField.setFont(font.deriveFont(15f));
		classField.setBounds(105, 40, 100, 20);
		upPanel.add(classField);

		JLabel posR = new JLabel("위치(행)   : ");
		posR.setFont(font.deriveFont(15f));
		posR.setBounds(25, 70, 100, 20);
		upPanel.add(posR);

		final JTextField posRField = new JTextField(5);
		posRField.setFont(font.deriveFont(15f));
		posRField.setBounds(105, 70, 100, 20);
		upPanel.add(posRField);

		JLabel posC = new JLabel("위치(열)   : ");
		posC.setFont(font.deriveFont(15f));
		posC.setBounds(25, 100, 100, 20);
		upPanel.add(posC);

		final JTextField posCField = new JTextField(5);
		posCField.setFont(font.deriveFont(15f));
		posCField.setBounds(105, 100, 100, 20);
		upPanel.add(posCField);

		final MyButton yesButton = new MyButton("확인");
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
					try {
						PCPost.getInstance().InitPost(classID, tempPosR, tempPposC);
					} catch (URISyntaxException | IOException e1) {
						e1.printStackTrace();
					}
					init.setVisible(false);
					PCUI.show();
					
				} else
					MessageBox.getInstance().show();

			}
		});
		yesButton.setBounds(40, 135, 60, 25);
		upPanel.add(yesButton);

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
				System.exit(0);
			}
		});
		noButton.setBounds(140, 135, 60, 25);
		upPanel.add(noButton);

		mainPanel.setLayout(null);
		mainPanel.add(upPanel);
		
		//init.getContentPane().add(mainPanel);
		init.setContentPane(new ShadowPane());
		init.add(mainPanel);
		init.setUndecorated(true);
		init.setVisible(true);
		init.setResizable(false);
		init.setSize(240, 170);
		init.setShape(new RoundRectangle2D.Double(0, 0, 240, 170, 10, 10));
		init.setLocationRelativeTo(null);
		init.setIconImage(ic.getImage());
		
	}
	
	private void initialize() {
		boolean createTable = CreateTable.getInstance().createTable();
		if (!createTable) {
			System.out.println("테이블 생성 실패!");
			System.exit(0);
		}
		
		ClassInfo classInfo = ReadData.getInstance().readData();

		if (classInfo == null)  // 읽을게 없다면
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
	public class ShadowPane extends JPanel {

        public ShadowPane() {
            setLayout(new BorderLayout());
            setOpaque(false);
            setBackground(Color.BLACK);
            setBorder(new EmptyBorder(0, 0, 3, 3));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }
}

