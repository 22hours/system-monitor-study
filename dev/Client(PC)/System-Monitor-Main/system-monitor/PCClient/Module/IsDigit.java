package PCClient.Module;

public class IsDigit {
	public static IsDigit instance;
	private IsDigit() {
		
	}
	public static IsDigit getInstance() {
		if(instance == null) {
			instance = new IsDigit();
		}
		return instance;
	}
	public boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
}
