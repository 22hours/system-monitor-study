package PCClient.Module;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

import com.sun.management.OperatingSystemMXBean;

public class Memory {
	OperatingSystemMXBean osBean = 
			(OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
	 RuntimeMXBean runbean = (RuntimeMXBean) ManagementFactory.getRuntimeMXBean();
	 public String showMemory() {
			try {
				double TotalSpace = (double)osBean.getTotalPhysicalMemorySize()/1024/1024/1024;
				double FreeSpace = (double)osBean.getFreePhysicalMemorySize()/1024/1024/1024;
				String memoryUsage = "";
				memoryUsage += String.format("%.2f", TotalSpace-FreeSpace);
				Thread.sleep(1000);
				return memoryUsage;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return null;
	 }
	 private Memory() {
		 
	 }
	 private static Memory instance = null;
	 public static Memory getMemory() {
		 if(instance==null) {
			 instance = new Memory();
		 }
		 return instance;
	 }
}
