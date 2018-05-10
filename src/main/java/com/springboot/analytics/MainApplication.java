package com.springboot.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.springboot.analytics")
public class MainApplication extends SpringBootServletInitializer {


	
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		return builder.sources(MainApplication.class);
	}

	public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


}
