package com.zoostarinc.portfolio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Generated
@Configuration
@ComponentScan(basePackages = { "net.zoostar", "com.zoostarinc" })
public class ApplicationContext {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		return security.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
				.oauth2Login(Customizer.withDefaults()).build();
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("https://portfolio.zoostarinc.com");
				log.debug("{}", "Completed CORS configuration.");
			}

		};
	}

}