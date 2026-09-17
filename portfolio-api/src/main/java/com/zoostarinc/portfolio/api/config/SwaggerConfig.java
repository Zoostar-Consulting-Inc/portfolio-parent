package com.zoostarinc.portfolio.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SwaggerConfig {

	@Value("${build.name}")
	private String buildName;

	@Value("${build.version}")
	private String buildVersion;

	@Value("${build.timestamp}")
	private String buildTimestamp;

	@Bean
	OpenAPI openAPI() {
		log.info("Configuring Swagger using build details: {}.{}.{}", buildVersion, buildName, buildTimestamp);
		var version = new StringBuilder(buildVersion).append(".").append(buildName).append(".").append(buildTimestamp);
		return new OpenAPI().info(new Info().title("Average Costing for Stock Portfolio")
				.description("Swagger UI Page for RESTful Portfolio JSON APIs.").version(version.toString())
				.contact(new Contact().name("zoostar").email("devops@zoostar.net")));
	}

}
