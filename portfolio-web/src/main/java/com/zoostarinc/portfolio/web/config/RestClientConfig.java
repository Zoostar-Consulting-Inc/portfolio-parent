package com.zoostarinc.portfolio.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

	@Bean
	RestClient restClient(OAuth2AuthorizedClientManager authorizedClientManager) {
		return RestClient.builder().requestInterceptor((request, body, execution) -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication instanceof OAuth2AuthenticationToken token
					&& token.getPrincipal() instanceof OidcUser oidcUser) {

				// Extract Google ID Token String
				String idTokenValue = oidcUser.getIdToken().getTokenValue();
				request.getHeaders().setBearerAuth(idTokenValue);
			}
			return execution.execute(request, body);
		}).build();
	}
}
