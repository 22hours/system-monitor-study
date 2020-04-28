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
import java.awt.Panel;
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
import PCClient.Module.GetMACAddress;
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
					// window.frame.setVisible(true);
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

		GetMACAddress MAC = new GetMACAddress();
		String pid = MAC.getLocalMacAddress();
		JTextField zField = new JTextField(5);
		JTextField xField = new JTextField(5);
		JTextField yField = new JTextField(5);
		JTextField wField = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Id:"));
		myPanel.add(wField);
		myPanel.add(new JLabel("classId:"));
		myPanel.add(zField);
		myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		myPanel.add(new JLabel("posR:"));
		myPanel.add(xField);
		myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		myPanel.add(new JLabel("posC:"));
		myPanel.add(yField);

		int result = JOptionPane.showConfirmDialog(null, myPanel, pid, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String Id = wField.getText();
			String classId = zField.getText();
			int x = Integer.parseInt(xField.getText());
			int y = Integer.parseInt(yField.getText());
			try {
				PCPost.getInstance().PostMethod(Id, classId, x, y);
			} catch (URISyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
