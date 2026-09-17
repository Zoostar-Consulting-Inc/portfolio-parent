package com.zoostarinc.portfolio.web;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public abstract class AbstractCommonTest {

	@MockitoBean
	protected OAuth2AuthorizedClientManager authorizedClientManager;

	@MockitoBean
	protected ClientRegistrationRepository clientRegistrationRepository;

	protected OidcUser oidcUser() {
		Map<String, Object> claims = new HashMap<>();
		claims.put(StandardClaimNames.SUB, "junit");
		claims.put(StandardClaimNames.GIVEN_NAME, "junit");
		claims.put(StandardClaimNames.EMAIL, "test@example.com");
		OidcIdToken idToken = new OidcIdToken("tokenValue", Instant.now(), Instant.now().plusSeconds(3600), claims);
		OidcUserInfo userInfo = new OidcUserInfo(claims);
		return new DefaultOidcUser(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), idToken,
				userInfo);
	}

}
