package PCClient.Module;

import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import PCClient.Http.PCPost;
import PCModel.PC;


public class PostLongPolling3 extends Thread{
	PC pc = null;

	public PostLongPolling3(PC pc) {
		this.pc = pc;
	}
	public void run() {
		Thread.currentThread().setName("Post");
		try {
			PCPost.getInstance().PostMethod(pc);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("PostLongPolling ³¡!");

	}
}
