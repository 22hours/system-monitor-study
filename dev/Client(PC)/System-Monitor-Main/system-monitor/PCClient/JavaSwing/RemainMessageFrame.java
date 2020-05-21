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
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import PCClient.JavaSwing.UserInfo.ShadowPane;
import sun.applet.Main;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class RemainMessageFrame {
	JFrame remainMessage = new JFrame();
	JLabel message = new JLabel();
	int posX;
	int posY;
	private Boolean isit = false;

	private static RemainMessageFrame instance = null;

	private RemainMessageFrame() {
		initialize();
	}

	public static RemainMessageFrame getInstance() {
		if (instance == null)
			instance = new RemainMessageFrame();
		return instance;
	}

	public synchronized void show(String message) {
		this.message.setText(message);

		if (isit == false)
			initialize();

		remainMessage.setLocationRelativeTo(null);

		if (!remainMessage.isVisible()) {
			remainMessage.setVisible(true);
		} else {
			remainMessage.setVisible(false);
			remainMessage.setVisible(true);
		}
	}

	private void initialize() {
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
		upPanel.setBounds(0, 0, 200, 120); // x y x크기 y크기
		upPanel.setBackground(Color.WHITE);
		upPanel.setLayout(null);

		JLabel header = new JLabel("종료 알람");
		header.setFont(font.deriveFont(20f));
		header.setBounds(60, 15, 200, 25);
		upPanel.add(header);

		this.message.setFont(font.deriveFont(15f));
		this.message.setBounds(50, 45, 200, 25);
		upPanel.add(this.message);

		final MyButton okayButton = new MyButton("확인");
		okayButton.setFont(font.deriveFont(12f));
		okayButton.setFocusPainted(false);
		okayButton.setBackground(new Color(192, 192, 192));
		okayButton.setForeground(Color.WHITE);
		okayButton.setBorder(null);
		okayButton.setHoverBackgroundColor(Color.BLACK);
		okayButton.setPressedBackgroundColor(Color.BLACK);
		okayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remainMessage.setVisible(false);
			}
		});
		okayButton.setBounds(70, 80, 65, 25);
		upPanel.add(okayButton);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.add(upPanel);

		remainMessage.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}
		});
		remainMessage.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent evt) {
				// sets frame position when mouse dragged
				Rectangle rectangle = remainMessage.getBounds();
				remainMessage.setBounds(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY, rectangle.width,
						rectangle.height);
			}
		});
		remainMessage.setContentPane(new ShadowPane());
		remainMessage.add(mainPanel);
		remainMessage.setUndecorated(true);
		remainMessage.setVisible(false);
		remainMessage.setResizable(false);
		remainMessage.setSize(200, 120);
		remainMessage.setShape(new RoundRectangle2D.Double(0, 0, 200, 120, 10, 10));
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
