package PCClient.JavaSwing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import PCClient.Http.PCGet;
import PCClient.Http.PCPost;
import PCClient.Module.*;
import PCClient.Module.Shutdown;
import PCModel.PC;
public class Frame {

	public static void main(String[] args) {
		GetMACAddress MAC = new GetMACAddress();
		String id = MAC.getLocalMacAddress();
		PC pc = new PC(id);
		/*try {
			PCGet.getInstance().GetMethod(pc);;
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*ShutdownHook s = new ShutdownHook(pc);
		s.AttachShutdownHook();*/
		Thread PostLongThread = new PostLongPolling(pc);
		Thread PostGeneralThread = new PostGeneralPolling(pc);
		Thread GetLongThread = new GetLongPolling(pc);
		PostLongThread.start();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GetLongThread.start();
		PostGeneralThread.start();
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PCPost.getInstance().Extension(pc, "2");
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JLabel label = new JLabel("22Hours");
		JButton btn1 = new JButton("Click");
		JButton btn2 = new JButton("Exit");
		final JTextArea textArea = new JTextArea();
		JPanel btnPanel = new JPanel();
		btnPanel.add(btn1);
		btnPanel.add(btn2);
		panel.setLayout(new BorderLayout());
		panel.add(label,BorderLayout.NORTH);
		panel.add(btnPanel,BorderLayout.WEST);
		panel.add(textArea,BorderLayout.CENTER);
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append("22Hours CopyRight\n");
				textArea.append(CPU.getCPU().showCPU());
				textArea.append(Memory.getMemory().showMemory());
			}
			
		});
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		frame.add(panel);
		
		frame.setVisible(true); // default가 false라 바꿔주기
		frame.setPreferredSize(new Dimension(840, 840*12/9));
		frame.setSize(840,840/12*9);
		frame.setLocationRelativeTo(null); // 가운데에서 Frame 출력하기
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 눌렀을 때 닫아주기(원래는 살아 있음)
		frame.setResizable(false); // 사용자가 크기 조절 못하게
		*/
	}

}
