package com.zoostarinc.portfolio.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfig {

	@Value("${spring.security.oauth2.client.registration.google.clientId:junit}")
	private String googleClientId;

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

}
