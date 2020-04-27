package com.hours22.system_monitor_ver11;

import javax.servlet.Filter;
import javax.servlet.annotation.WebServlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

/*
@WebServlet(asyncSupported = true)
public class FilterRegistration {
    @Bean
    public Filter filter(){
        ShallowEtagHeaderFilter filter=new ShallowEtagHeaderFilter();
        return filter;
    }
}

*/