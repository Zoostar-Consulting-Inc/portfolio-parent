package com.zoostarinc.portfolio.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.validation.support.BindingAwareModelMap;

import com.zoostarinc.portfolio.service.impl.DefaultPortfolioApiService;
import com.zoostarinc.portfolio.web.AbstractCommonTest;

//@AutoConfigureMockMvc
//@AutoConfigureMockRestServiceServer
@RestClientTest(DefaultPortfolioApiService.class)
class PortfolioControllerTest extends AbstractCommonTest {

	@Value("${base-url.portfolio-api}")
	private String portfolioApiBaseUrl;

	@Autowired
	MockRestServiceServer mockRestServer;
	
	@Autowired
	PortfolioController portfolioController;

	@Test
	void testIndex200() {
		// given
		BindingAwareModelMap model = new BindingAwareModelMap();
		
		// mock
		String response = "{}";
		mockRestServer.expect(requestTo(portfolioApiBaseUrl + "/summary")).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
		
		// when
		var page = portfolioController.index(oidcUser(), model);

		// then
		assertThat(page).isEqualTo("portfolio");
	}

}
