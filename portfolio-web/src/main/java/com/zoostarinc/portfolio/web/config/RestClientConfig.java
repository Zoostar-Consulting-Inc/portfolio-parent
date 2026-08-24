package com.zoostarinc.portfolio.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

	@Bean
	RestClient restClient() {
		return RestClient.builder().baseUrl("http://localhost:9080/portfolio-api").
				defaultHeader("Accept", "application/json").build();
	}

}
