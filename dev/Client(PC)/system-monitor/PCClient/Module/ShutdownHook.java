package PCClient.Module;

public class ShutdownHook {
	public void AttachShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Program is shutting down");
			}
		});
		System.out.println("Shut Down Hook Attached...");
	}
}
