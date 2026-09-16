package com.zoostarinc.portfolio.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestClientConfig {

	@Bean
	RestClient restClient(RestClient.Builder builder, OAuth2AuthorizedClientManager authorizedClientManager) {
		log.info("{}...", "Configuring RestClient with OAuth2 token relay");
		return builder.requestInterceptor((request, body, execution) -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication instanceof OAuth2AuthenticationToken token
					&& token.getPrincipal() instanceof OidcUser oidcUser) {

				// Extract Google ID Token String
				log.info("Extracting ID Token for user: {}", oidcUser.getEmail());
				String idTokenValue = oidcUser.getIdToken().getTokenValue();
				request.getHeaders().setBearerAuth(idTokenValue);
			}
			return execution.execute(request, body);
		}).build();
	}
}
