package PCClient.JavaSwing;

import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class Frame {

	public static void main(String[] args) {
		/*GetMACAddress MAC = new GetMACAddress();
		String id = MAC.getLocalMacAddress();
		PC pc = new PC(id);
		try {
			PCPost.getInstance().PostMethod(pc);
			PCGet.getInstance().GetMethod(pc);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*try {
			PCGet.getInstance().GetMethod(pc);;
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*ShutdownHook s = new ShutdownHook(pc);
		s.AttachShutdownHook();*/
		/*Thread PostLongThread = new PostLongPolling(pc);
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
		}*/
		JFrame frame = new JFrame("22Hours");
		frame.setVisible(true); // default가 false라 바꿔주기
		frame.setSize(100,100);
		frame.setLocationRelativeTo(null); // 가운데에서 Frame 출력하기
		frame.setResizable(true); // 사용자가 크기 조절 못하게
		ImagePanel panel = new ImagePanel(new ImageIcon("./img/tag-bg.jpg").getImage());
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 눌렀을 때 닫아주기(원래는 살아 있음)

	}

}
