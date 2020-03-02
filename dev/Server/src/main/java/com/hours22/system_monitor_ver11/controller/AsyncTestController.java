package com.hours22.system_monitor_ver11.controller;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/async/callable", method = RequestMethod.GET)
public class AsyncTestController {
 
    @RequestMapping(value = "/response/{msg}", method = RequestMethod.GET)
    public @ResponseBody Callable<String> callable(@PathVariable String msg) {
    	System.out.println("Start method !! [ " + msg +" ]");
        return new Callable<String>() {
            @Override
            public String call()throws Exception {
                Thread.sleep(30000);
                System.out.println("End method !! [" +msg+ " ]");
                return "Callable result" + msg;
            }
        };
    }
}