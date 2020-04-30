package com.hours22.system_monitor_ver11.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hours22.system_monitor_ver11.client.ServerTimer;

@Controller
//@RequestMapping(value = "/async/callable", method = RequestMethod.GET)
public class AsyncTestController {
 
	String InterruptKey = null;
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
    @RequestMapping(value = "/response/{id}/{msg}", method = RequestMethod.GET)
    public @ResponseBody Callable<String> callable(HttpServletRequest request, HttpServletResponse response, @PathVariable String msg, @PathVariable String id) {
    	System.out.println("-----------------------------------------------------------------------------------------------");
    	System.out.println("Start method !! at " +  transFormat.format(new Date()));
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
            	long MSG = ServerTimer.GetTime(msg);
            	System.out.println(MSG / 1000 + "초 동안 Thread.sleep();");
            	System.out.println("Endtime is " + msg);
                //Thread.sleep(252000);
                // start
                
                long timeToSleep = MSG;
                long start, end, slept;
                boolean interrupted = false;

                while(timeToSleep > 0 && InterruptKey != id){
                    start=System.currentTimeMillis();
                    try{
                        Thread.sleep(timeToSleep);
                        break;
                    }
                    catch(InterruptedException e){
                        //work out how much more time to sleep for
                        end=System.currentTimeMillis();
                        slept=end-start;
                        timeToSleep-=slept;
                        interrupted=true;
                        System.out.println("Thread Interrupt ! at " + transFormat.format(new Date()));
                    }
                }
                if(interrupted){
                    //restore interruption before exit
                    Thread.currentThread().interrupt();
                }
                
                // end
                System.out.println("-----------------------------------------------------------------------------------------------");
                System.out.println("End Method !! at "+transFormat.format(new Date()));
                InterruptKey = null;
                return "Callable result " + msg;
            }
        };
    }
    @RequestMapping(value = "/threads/{thName}", method = RequestMethod.GET)
    public void GetThreadIdTest(@PathVariable String thName) {
    	Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();

		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("Input : /async/callable/threads/"+thName);
		System.out.println("GetThreadIdTesting.....");
		System.out.println("현재 Thread ID : " + Thread.currentThread().getId());  
    	//Iterate over set to find yours
    	
    	for(Thread thread : setOfThread){
    		System.out.println("Active Thread's [ Number : " +thread.getId()+" / Name : "+thread.getName()+" ] ");
    	    
    		String res = thread.getName();
    		if(res.equals(thName)) {
    			thread.interrupt();
    			System.out.println("******"+res+" 스레드를 종료시킵니다.******");
    		}
    	}
    }
    
	@Async("threadPoolHome")
    @RequestMapping(value = "/test22", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<Map<String, String>>> GetThreadIdTest22() {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("key", "1");
    	map.put("keys", "2");

  
    	String info = "123123123";
    	System.out.println("ID : "+Thread.currentThread().getId());
    	System.out.println("NAME : "+Thread.currentThread().getName());
    	System.out.println(info);
    	return CompletableFuture.completedFuture(ResponseEntity.accepted().body(map));
    }
}