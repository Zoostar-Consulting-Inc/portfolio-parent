package com.zoostarinc.portfolio;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Generated
@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@EnableTransactionManagement
public class ApplicationContext implements DisposableBean {

	@Value("${build.name}")
	private String buildName;

	@Value("${build.version}")
	private String buildVersion;

	@Value("${build.timestamp}")
	private String buildTimestamp;
	
	private final DataSource dataSource;

	@Bean
	OpenAPI openAPI() {
		var version = new StringBuilder(buildVersion).append(".").append(buildName).append(".").append(buildTimestamp);
		return new OpenAPI().info(new Info().title("Stock Portfolio").description("Swagger UI Page for Portfolio JSON APIs.")
				.version(version.toString()).contact(new Contact().name("zoostar").email("devops@zoostar.net")));
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		return security.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
				// Allow Swagger UI resources (CSS, JS, HTML, images)
				.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/webjars/**").permitAll()
				// Allow static resources if you have any served directly
				.requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
				.anyRequest().authenticated()).oauth2Login(Customizer.withDefaults()).build();
	}

	@Override
	public void destroy() throws Exception {
		closeDataSource(dataSource);
	}

	public static void closeDataSource(DataSource dataSource) {
		if(dataSource != null) {
			Connection conn;
			try {
				conn = dataSource.getConnection();
				log.info("Performing a clean shutdown of connection: {}...", conn);
				conn.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}