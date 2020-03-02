package com.hours22.system_monitor_ver11.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hours22.system_monitor_ver11.client.ServerTimer;

@Controller
@RequestMapping(value = "/async/callable", method = RequestMethod.GET)
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
}