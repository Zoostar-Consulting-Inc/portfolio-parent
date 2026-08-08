package com.zoostarinc.portfolio;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractCommonTest {

	@MockitoBean
	ClientRegistrationRepository clientRegistrationRepository;

	@Autowired
	protected MockMvc endpoint;

	protected ObjectMapper om = objectMapper();

	protected ObjectMapper objectMapper() {
		return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	protected MockHttpServletResponse getResponse(String url) throws Exception {
		return endpoint.perform(get(url).with(oidcLogin().oidcUser(oidcUser()))).andReturn().getResponse();
	}

	protected MockHttpServletResponse getJsonResponse(String url) throws Exception {
		return endpoint.perform(get(url).contentType(MediaType.APPLICATION_JSON).with(oidcLogin().oidcUser(oidcUser())))
				.andReturn().getResponse();
	}

	protected MockHttpServletResponse getJsonResponse(String url, String paramName, String... paramValues)
			throws Exception {
		return endpoint.perform(get(url).param(paramName, paramValues).contentType(MediaType.APPLICATION_JSON)
				.with(oidcLogin().oidcUser(oidcUser()))).andReturn().getResponse();
	}

	protected <T> MockHttpServletResponse postJsonRequest(String url, T request) throws Exception {
		return endpoint
				.perform(post(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.content(om.writeValueAsString(request)).with(oidcLogin().oidcUser(oidcUser())))
				.andReturn().getResponse();
	}

	protected OidcUser oidcUser() {
		Map<String, Object> claims = new HashMap<>();
		claims.put(StandardClaimNames.SUB, "testuser");
		claims.put(StandardClaimNames.GIVEN_NAME, "junit");
		claims.put(StandardClaimNames.EMAIL, "test@example.com");
		OidcIdToken idToken = new OidcIdToken("tokenValue", Instant.now(), Instant.now().plusSeconds(3600), claims);
		OidcUserInfo userInfo = new OidcUserInfo(claims);
		return new DefaultOidcUser(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), idToken,
				userInfo);
	}

}
