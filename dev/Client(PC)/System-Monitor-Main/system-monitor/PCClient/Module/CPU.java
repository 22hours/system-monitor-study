package PCClient.Module;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

import com.sun.management.OperatingSystemMXBean;

public class CPU { // 하나의 클래스만 존재해도 된다 생각해서 Singleton으로 만들었습니다.
	OperatingSystemMXBean osBean = 
			(OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
	 RuntimeMXBean runbean = (RuntimeMXBean) ManagementFactory.getRuntimeMXBean();
	public String showCPU() {
		try {
			String cpuUsage = "";
			cpuUsage += String.format("%.2f", osBean.getSystemCpuLoad() * 100);
			Thread.sleep(1000);
			return cpuUsage;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private CPU() {
		
	}
	
	private static CPU instance = null;
	public static CPU getCPU() {
		if(instance==null) {
			instance = new CPU();
		}
		return instance;
	}
}
