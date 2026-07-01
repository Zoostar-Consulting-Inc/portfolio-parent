package com.zoostarinc.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import lombok.Generated;

@Generated
@SpringBootApplication
@EnableAspectJAutoProxy
public class PortfolioSpringWeb {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioSpringWeb.class, args);
	}

}
