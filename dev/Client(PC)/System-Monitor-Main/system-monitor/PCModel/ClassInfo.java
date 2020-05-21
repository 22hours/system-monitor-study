package PCModel;

public class ClassInfo {
	private String name;
	private String classID;
	private int posR;
	private int posC;

	public ClassInfo(String name, String classID, int posR, int posC) {
		super();
		this.name = name;
		this.classID = classID;
		this.posR = posR;
		this.posC = posC;
	}

	public String toString() {
		String str = "name = " + name + "\nclassID = " + classID + "\nposR = " + posR + "\nposC = " + posC;
		return str;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public int getPosR() {
		return posR;
	}

	public void setPosR(int posR) {
		this.posR = posR;
	}

	public int getPosC() {
		return posC;
	}

	public void setPosC(int posC) {
		this.posC = posC;
	}

}
