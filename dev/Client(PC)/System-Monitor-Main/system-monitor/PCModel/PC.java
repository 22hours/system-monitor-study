package PCModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import PCClient.Module.*;
import sun.org.mozilla.javascript.internal.json.JsonParser.ParseException;

public class PC {
	private String power_status = null;
	private String id = null;
	private String name = null;
	private String class_id = null;
	private String start_time = null;
	private String end_time = null;
	private String cpu_data = null;
	private String ram_data = null;
	private String remainTime = null;
	private int posR = -1;
	private int posC = -1;
	private String type = "PC";

	public PC(String id) {
		this.id = id;
		this.power_status = "ON"; // �� Ŭ������ instanceȭ ��Ű�� ���� �ִٴ� ���̴�.
		// 60,000 = 1��, 600,000 = 10��
		// default ���� 3�ð��̶�� ���� = 3,600,000 * 3 = 10,800,000
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		this.start_time = dayTime.format(new Date(time));
		time += 10800000; // 3�ð�
		//time+=1920000; //32�� �� �ӽ� ������
		//time+=300000; // 5�� ��
		this.end_time = dayTime.format(new Date(time));
		this.cpu_data = CPU.getCPU().showCPU();
		this.ram_data = Memory.getMemory().showMemory();
		this.remainTime = "30";
		this.class_id = "D404";
	}

	public String getPower_status() {
		return power_status;
	}

	public void setPower_status(String power_status) {
		this.power_status = power_status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getCpu_data() {
		return cpu_data;
	}

	public void setCpu_data(String cpu_data) {
		this.cpu_data = cpu_data;
	}

	public String getRam_data() {
		return ram_data;
	}

	public void setRam_data(String ram_data) {
		this.ram_data = ram_data;
	}

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
