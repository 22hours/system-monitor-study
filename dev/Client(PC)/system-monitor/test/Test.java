package test;

public class Test {
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new shutdownhook());
		/*while(true) {
			System.out.println("This is running");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
}
