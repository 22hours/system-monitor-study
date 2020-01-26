package PCModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import PCClient.Module.*;
import sun.org.mozilla.javascript.internal.json.JsonParser.ParseException;

public class PC {
	private String power_status;
	private String id;
	private String start_time;
	private String end_time;
	private String cpu_data;
	private String ram_data;

	public PC(String id) {
		this.id = id;
		this.power_status = "true"; // 이 클래스를 instance화 시키면 켜져 있다는 뜻이다.
		// 60,000 = 1분, 600,000 = 10분
		// default 값을 3시간이라고 가정 = 3,600,000 * 3 = 10,800,000
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.start_time = dayTime.format(new Date(time));
		time += 10800000;
		this.end_time = dayTime.format(new Date(time));
		this.cpu_data = CPU.getCPU().showCPU();
		this.ram_data = Memory.getMemory().showMemory();
		
	}

	public String getId() {
		return this.id;
	}

	public String getPower_status() {
		return this.power_status;
	}

	public void setPower_status(String power_status) {
		this.power_status = power_status;
	}

	public String getStart_time() {
		return this.start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return this.end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getCpu_data() {
		return this.cpu_data;
	}

	public void setCpu_data(String cpu_data) {
		this.cpu_data = cpu_data;
	}

	public String getRam_data() {
		return this.ram_data;
	}

	public void setRam_data(String ram_data) {
		this.ram_data = ram_data;
	}
}
