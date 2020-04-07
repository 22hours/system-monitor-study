package test;

import java.awt.Window;

public class Test {
	public static void main(String[] args) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Window activeWindow = javax.swing.FocusManager.getCurrentManager().getActiveWindow();
		System.out.println(activeWindow);
	}
}
