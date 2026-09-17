package com.zoostarinc.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import lombok.Generated;

@Generated
@SpringBootApplication
@ComponentScan(basePackages = { "net.zoostar", "com.zoostarinc" })
public class PortfolioSpringBootAPI extends SpringBootServletInitializer {

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(PortfolioSpringBootAPI.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PortfolioSpringBootAPI.class, args);
	}

}
