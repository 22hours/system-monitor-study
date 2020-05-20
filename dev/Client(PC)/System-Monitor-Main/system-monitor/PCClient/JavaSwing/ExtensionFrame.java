package PCClient.JavaSwing;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import PCClient.Http.PCPost;
import PCClient.Module.PCExtension;
import PCModel.PC;
import sun.applet.Main;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class ExtensionFrame {
	int posX;
	int posY;
	JFrame extensionFrame = new JFrame();
	JLabel msg = new JLabel();
	JComboBox<String> combo;
	private DefaultListCellRenderer listRenderer;
	private static ExtensionFrame instance = null;
	private PC pc = null;
	private Boolean isit = false;

	private ExtensionFrame() {
	}

	public static ExtensionFrame getInstance() {
		if (instance == null)
			instance = new ExtensionFrame();
		return instance;
	}

	public synchronized void show(JFrame jframe, PC pc) {
		this.pc = pc;
		if (isit == false)
			initialize();
		extensionFrame.setLocationRelativeTo(null);
		if (!extensionFrame.isVisible()) {
			extensionFrame.setVisible(true);
		}
	}

	private void initialize() {
		String extensionTime[] = { "1시간", "2시간", "3시간", "4시간", "5시간", "6시간" };
		isit = true;
		URL dohyeonURL = getClass().getClassLoader().getResource("MapoGoldenPier.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, dohyeonURL.openStream());

		} catch (FontFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		JPanel upPanel = new JPanel();
		upPanel.setBounds(0, 0, 300, 60); // x y x크기 y크기
		upPanel.setBackground(new Color(192, 192, 192));
		upPanel.setLayout(null);

		JLabel header = new JLabel("PC 연장 신청");
		header.setFont(font.deriveFont(20f));
		header.setBounds(88, 5, 150, 50);
		header.setForeground(Color.WHITE);
		upPanel.add(header);

		JPanel downPanel = new JPanel();
		downPanel.setBounds(0, 60, 300, 230);
		downPanel.setBackground(Color.WHITE);
		downPanel.setLayout(null);

		JLabel msgExtension = new JLabel("연장 신청");
		msgExtension.setFont(font.deriveFont(15f));
		msgExtension.setBounds(118, 18, 100, 20);
		downPanel.add(msgExtension);
		
		JLabel resultMessage = new JLabel("연장 신청 후 종료 예정 시간");
		resultMessage.setFont(font.deriveFont(15f));
		resultMessage.setBounds(65, 108, 200, 20);
		downPanel.add(resultMessage);

		msg.setBounds(80, 138, 140, 20);
		msg.setBorder(new LineBorder(Color.LIGHT_GRAY));
		msg.setFont(font.deriveFont(15f));
		String defaultTime = PCExtension.getInstance().Extension(pc, "1");
		msg.setText(defaultTime);
		downPanel.add(msg);

		combo = new JComboBox<String>(extensionTime);
		combo.setBounds(45, 48, 220, 30);
		combo.setFont(font.deriveFont(13f));
		listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
		combo.setRenderer(new TwoDecimalRenderer(listRenderer));
		combo.setForeground(Color.BLACK);
		combo.setBackground(Color.WHITE);
		combo.setFocusable(false);
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String time = combo.getSelectedItem().toString();
				time = time.substring(0, 1);
				String extensionTime = PCExtension.getInstance().Extension(pc, time);
				msg.setText(extensionTime);
			}
		});
		downPanel.add(combo);

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
				String time = combo.getSelectedItem().toString();
				time = time.substring(0, 1);
				doPost(time);
				extensionFrame.setVisible(false);
				combo.setSelectedIndex(0);
				String defaultTime = PCExtension.getInstance().Extension(pc, "1");
				msg.setText(defaultTime);
			}
		});
		yesButton.setBounds(70, 188, 60, 25);
		downPanel.add(yesButton);

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
				extensionFrame.setVisible(false);
				combo.setSelectedIndex(0);
				String defaultTime = PCExtension.getInstance().Extension(pc, "1");
				msg.setText(defaultTime);
			}
		});
		noButton.setBounds(170, 188, 60, 25);
		downPanel.add(noButton);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.add(upPanel);
		mainPanel.add(downPanel);

		// userInfo.getContentPane().add(mainPanel);
		extensionFrame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}
		});
		extensionFrame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent evt) {
				// sets frame position when mouse dragged
				Rectangle rectangle = extensionFrame.getBounds();
				extensionFrame.setBounds(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY, rectangle.width,
						rectangle.height);
			}
		});
		extensionFrame.setContentPane(new ShadowPane());
		extensionFrame.add(mainPanel);
		extensionFrame.setUndecorated(true);
		extensionFrame.setVisible(false);
		extensionFrame.setResizable(false);
		extensionFrame.setSize(300, 290);
		extensionFrame.setShape(new RoundRectangle2D.Double(0, 0, 300, 290, 10, 10));
	}

	private void doPost(String time) {
		PCExtension.getInstance().ExtensionService(pc, time);
		try {
			PCPost.getInstance().PostMethod(pc);
		} catch (URISyntaxException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
