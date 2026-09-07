package com.zoostarinc.portfolio.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.zoostarinc.portfolio.AbstractMockRepository;
import com.zoostarinc.portfolio.api.request.PositionRequest;
import com.zoostarinc.portfolio.api.response.PositionResponse;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class PortfolioTransactionControllerTest extends AbstractMockRepository {

	@Test
	void testBuy() throws Exception {
		// given
		var url = "/buy";
		PositionRequest request = new PositionRequest();
		request.setTicker("JUNIT");
		request.setQuantity(10);
		request.setAmount(1000f);
		request.setDate(LocalDate.now());

		// mock
		var position = new PositionEntity();
		position.setAmount(request.getAmount());
		position.setDate(request.getDate());
		position.setQuantity(request.getQuantity());
		position.setTicker(request.getTicker());
		position.setUserId(oidcUser().getSubject());

		String positionId = "1";
		var entity = new PositionEntity(positionId, position.getUserId(), position.getTicker(), position.getDate(),
				position.getQuantity(), position.getAmount());

		when(positionRepository.save(position)).thenReturn(entity);

		// when
		var response = postJsonRequest(url, request);

		// then
		assertThat(response.getStatus()).isEqualTo(200);
		var result = om.readValue(response.getContentAsString(), PositionResponse.class);
		assertThat(result.getPositionId()).isEqualTo(positionId);
		assertThat(result.getTicker()).isEqualTo(request.getTicker());
		assertThat(result.getQuantity()).isEqualTo(request.getQuantity());
		assertThat(result.getAmount()).isEqualTo(request.getAmount());
		assertThat(result.getDate()).isEqualTo(request.getDate());
	}

	@Test
	void testSell() throws Exception {
		// given
		var url = "/sell";
		PositionRequest request = new PositionRequest();
		request.setTicker("JUNIT");
		request.setQuantity(100);
		request.setAmount(1000f);
		request.setDate(LocalDate.now());

		// mock
		var position = new PositionEntity();
		position.setAmount(request.getAmount() * -1);
		position.setDate(request.getDate());
		position.setQuantity(request.getQuantity() * -1);
		position.setTicker(request.getTicker());
		position.setUserId(oidcUser().getSubject());

		String positionId = "1";
		var entity = new PositionEntity(positionId, position.getUserId(), position.getTicker(), position.getDate(),
				position.getQuantity(), position.getAmount());

		when(positionRepository.save(position)).thenReturn(entity);

		// when
		var response = postJsonRequest(url, request);

		// then
		assertThat(response.getStatus()).isEqualTo(200);
		var result = om.readValue(response.getContentAsString(), PositionResponse.class);
		assertThat(result.getPositionId()).isEqualTo(positionId);
		assertThat(result.getTicker()).isEqualTo(request.getTicker());
		assertThat(result.getQuantity()).isEqualTo(request.getQuantity() * -1);
		assertThat(result.getAmount()).isEqualTo(request.getAmount() * -1);
		assertThat(result.getDate()).isEqualTo(request.getDate());
	}

	@Test
	void testBuy400() throws Exception {
		// given
		var url = "/buy";
		PositionRequest request = new PositionRequest();
		request.setTicker("JUNIT");
		request.setQuantity(-10);
		request.setAmount(1000f);
		request.setDate(LocalDate.now());

		// when
		var response = postJsonRequest(url, request);

		// then
		assertThat(response.getStatus()).isEqualTo(400);
		String message = response.getContentAsString();
		log.info("Response: {}", message);
		assertThat(message).contains("Quantity must be greater than zero!");
	}

}
