package com.zoostarinc.portfolio.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security, JwtDecoder jwtDecoder) throws Exception {
		return security.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						// Allow preflight requests for CORS from front-end clients
						.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
						// Allow Swagger UI resources (CSS, JS, HTML, images)
						.requestMatchers("/swagger-ui/*.css", "/swagger-ui/*.js", "/v3/api-docs/**", "/webjars/**",
								"/static/**")
						.permitAll().anyRequest().authenticated())
				.oauth2Login(Customizer.withDefaults())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder))).build();
	}

}
