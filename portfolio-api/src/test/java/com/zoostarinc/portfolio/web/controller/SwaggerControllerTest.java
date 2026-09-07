package com.zoostarinc.portfolio.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.zoostarinc.portfolio.AbstractMockRepository;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerControllerTest extends AbstractMockRepository {

	@Test
	void testHome() throws Exception {
		// given
		String url = "/";

		// when
		var response = getResponse(url);

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
	}

}
