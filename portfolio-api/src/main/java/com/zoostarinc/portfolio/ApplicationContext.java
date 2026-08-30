package com.zoostarinc.portfolio;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Generated
@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@ComponentScan(basePackages = { "net.zoostar", "com.zoostarinc" })
//@EnableTransactionManagement
public class ApplicationContext {

	@Value("${build.name}")
	private String buildName;

	@Value("${build.version}")
	private String buildVersion;

	@Value("${build.timestamp}")
	private String buildTimestamp;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Value("${spring.security.oauth2.client.registration.google.clientId:junit}")
	private String googleClientId;

	@Bean
	OpenAPI openAPI() {
		var version = new StringBuilder(buildVersion).append(".").append(buildName).append(".").append(buildTimestamp);
		return new OpenAPI().info(new Info().title("Average Costing for Stock Portfolio")
				.description("Swagger UI Page for RESTful Portfolio JSON APIs.").version(version.toString())
				.contact(new Contact().name("zoostar").email("devops@zoostar.net").url(contextPath)));
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		return security.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						// Allow preflight requests for CORS from front-end clients
						.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
						// Allow Swagger UI resources (CSS, JS, HTML, images)
						.requestMatchers("/swagger-ui/*.css", "/swagger-ui/*.js", "/v3/api-docs/**", "/webjars/**",
								"/static/**")
						.permitAll().anyRequest().authenticated())
				.oauth2Login(Customizer.withDefaults())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder()))).build();
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				log.info("{}...", "Configuring CORS Mapping");
				registry.addMapping("/**").allowedOrigins("https://portfolio.apigator.net", "http://localhost:1080")
						.allowedMethods("GET", "POST", "OPTIONS").allowedHeaders("*").allowCredentials(true);
			}

		};

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:1080"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	JwtDecoder jwtDecoder() {
		// Points to Google's public issuer endpoint
		NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation("https://accounts.google.com");

		OAuth2TokenValidator<Jwt> audienceValidator = new JwtClaimValidator<List<String>>("aud",
				aud -> aud != null && aud.contains(googleClientId));
		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer("https://accounts.google.com");
		OAuth2TokenValidator<Jwt> combinedValidator = new DelegatingOAuth2TokenValidator<>(withIssuer,
				audienceValidator);

		jwtDecoder.setJwtValidator(combinedValidator);
		return jwtDecoder;
	}

	@Bean
	ObjectMapper objectMapper() {
		// Create and configure the mapper
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// 1. Register the Java 8 Date/Time module
		objectMapper.registerModule(new JavaTimeModule());
	
		// 2. (Optional but Recommended) Prevent Jackson from writing Instants/Dates as timestamps (like [2026,8,29])
		// This forces it to use standard ISO-8601 strings (e.g., "2026-08-29T16:22:00Z")
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		return objectMapper;
	}
	
}