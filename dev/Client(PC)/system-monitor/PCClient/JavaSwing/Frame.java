package PCClient.JavaSwing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import PCClient.Http.PCGet;
import PCClient.Http.PCPost;
import PCClient.Module.*;
import PCModel.PC;
public class Frame {

	public static void main(String[] args) {
		//PC���� ������ �ּ� ���ͼ� id�� �ֱ�
		/*String id = "1"; // �ϴ� 1�̶�� ����
		PC pc = new PC(id);
		Thread generalPostThread = new GeneralPostPolling(pc);
		Thread generalGetThread = new GeneralGetPolling(pc);
		generalPostThread.start();
		generalGetThread.start();*/
		try {
			int i=1;
			ShutdownHook damin = new ShutdownHook();
			damin.AttachShutdownHook();
			while(true) {
				System.out.println("hi");
				Thread.sleep(1000);
				i++;
				if(i==3) break;
			}
		}
		catch(Exception e) {
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
		
		frame.setVisible(true); // default�� false�� �ٲ��ֱ�
		frame.setPreferredSize(new Dimension(840, 840*12/9));
		frame.setSize(840,840/12*9);
		frame.setLocationRelativeTo(null); // ������� Frame ����ϱ�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �ݱ� ��ư ������ �� �ݾ��ֱ�(������ ��� ����)
		frame.setResizable(false); // ����ڰ� ũ�� ���� ���ϰ�
		*/
	}

}
