package com.hours22.system_monitor_ver11;

import java.nio.charset.Charset;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    //@Bean
    //FilterRegistrationBean shallowEtagBean () {
    //    FilterRegistrationBean frb = new FilterRegistrationBean();
    //    frb.setFilter(new ShallowEtagHeaderFilter());
    //    frb.addUrlPatterns("/mobile/*");
    //    frb.setOrder(2);
    //    return frb;
    //}
}
