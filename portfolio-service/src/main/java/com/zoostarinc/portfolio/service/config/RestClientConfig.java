package com.zoostarinc.portfolio.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
@ComponentScan(basePackages = { "net.zoostar", "com.zoostarinc" })
public class RestClientConfig {

	private static final String SERVER_BASE_URL = "https://portfolio.apigator.net";

	@Bean
	RestClient oauth2RestClient(OAuth2AuthorizedClientManager authorizedClientManager) {
		OAuth2ClientHttpRequestInterceptor interceptor = new OAuth2ClientHttpRequestInterceptor(
				authorizedClientManager);
		return RestClient.builder().requestInterceptor(interceptor).baseUrl(SERVER_BASE_URL).build();
	}

}