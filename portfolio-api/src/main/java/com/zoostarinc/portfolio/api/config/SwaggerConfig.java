package com.zoostarinc.portfolio.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Value("${build.name}")
	private String buildName;

	@Value("${build.version}")
	private String buildVersion;

	@Value("${build.timestamp}")
	private String buildTimestamp;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Bean
	OpenAPI openAPI() {
		var version = new StringBuilder(buildVersion).append(".").append(buildName).append(".").append(buildTimestamp);
		return new OpenAPI().info(new Info().title("Average Costing for Stock Portfolio")
				.description("Swagger UI Page for RESTful Portfolio JSON APIs.").version(version.toString())
				.contact(new Contact().name("zoostar").email("devops@zoostar.net").url(contextPath)));
	}

}
