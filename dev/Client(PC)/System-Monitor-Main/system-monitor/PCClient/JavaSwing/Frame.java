package PCClient.JavaSwing;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import PCClient.Http.PCGet;
import PCClient.Http.PCPost;
import PCClient.Module.GetLongPolling;
import PCClient.Module.GetMACAddress;
import PCClient.Module.PostGeneralPolling;
import PCClient.Module.PostLongPolling;
import PCModel.PC;
public class Frame {

	public static void main(String[] args) {
		GetMACAddress MAC = new GetMACAddress();
		String id = MAC.getLocalMacAddress();
		PC pc = new PC(id);
		try {
			Thread PostGeneralThread = new PostLongPolling(pc);
			Thread GetLongThread = new GetLongPolling(pc);
			PostGeneralThread.start();
			Thread.sleep(15000);
			GetLongThread.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		}
*/
	}

}
