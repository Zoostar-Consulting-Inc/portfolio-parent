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
public class PortfolioSpringWeb extends SpringBootServletInitializer {

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(PortfolioSpringWeb.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PortfolioSpringWeb.class, args);
	}

}
