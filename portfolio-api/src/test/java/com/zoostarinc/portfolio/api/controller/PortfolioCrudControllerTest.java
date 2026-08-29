package com.zoostarinc.portfolio.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.zoostarinc.portfolio.AbstractCommonTest;
import com.zoostarinc.portfolio.api.request.PositionRequest;
import com.zoostarinc.portfolio.api.response.PositionResponse;
import com.zoostarinc.portfolio.api.response.PositionSummary;
import com.zoostarinc.portfolio.api.response.PositionSummaryResponse;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.dao.repository.PositionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class PortfolioCrudControllerTest extends AbstractCommonTest {

	@MockitoBean
	PositionRepository positionRepository;

	@Test
	void testBuy() throws Exception {
		// given
		var url = "/buy";
		PositionRequest request = new PositionRequest();
		request.setTicker("JUNIT");
		request.setQuantity(10);
		request.setAmount(1000f);
		request.setDate(Instant.now());

		// mock
		var position = new PositionEntity(oidcUser().getSubject(), request.getDate(), request.getTicker(),
				request.getQuantity(), request.getAmount());
		var entity = new PositionEntity(position.getUserId(), position.getDate(), position.getTicker(),
				position.getQuantity(), position.getAmount());
		String positionId = "1";
		entity.setId(positionId);

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
		request.setTicker("junit");
		request.setQuantity(10);
		request.setAmount(1000f);
		request.setDate(Instant.now());

		// mock
		var position = new PositionEntity(oidcUser().getSubject(), request.getDate(), request.getTicker(),
				request.getQuantity(), request.getAmount());
		var entity = new PositionEntity(position.getUserId(), position.getDate(), position.getTicker(),
				position.getQuantity(), position.getAmount());
		String positionId = "1";
		entity.setId(positionId);

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
	void testSummary() throws Exception {
		// given
		var url = "/summary";

		// mock
		when(positionRepository.findByUserIdAndTickerOrderByTickerAscQuantityDesc(oidcUser().getSubject(), ""))
				.thenReturn(List.of());

		// when
		var response = getJsonResponse(url);

		// then
		assertThat(response.getStatus()).isEqualTo(200);
		var result = om.readValue(response.getContentAsString(), PositionSummaryResponse.class);
		assertThat(result.getPositions()).isEmpty();
	}

	@Test
	void testTickerSummary() throws Exception {
		// given
		var url = "/summary";
		String tickerLabel = "ticker";
		String tickerValue = "JUNIT";

		// mock
		List<PositionEntity> entities = new ArrayList<>(1);
		entities.add(new PositionEntity("1", oidcUser().getSubject(), Instant.now(), tickerValue, 10, 1000f));
		when(positionRepository.findByUserIdAndTickerOrderByTickerAscQuantityDesc(oidcUser().getSubject(), tickerValue))
				.thenReturn(entities);

		// when
		var response = getJsonResponse(url, tickerLabel, tickerValue);

		// then
		assertThat(response.getStatus()).isEqualTo(200);
		var result = om.readValue(response.getContentAsString(), PositionSummaryResponse.class);
		assertThat(result.getPositions()).hasSize(1);
		PositionSummary entity = result.getPositions().get(1);
		assertThat(entity.getAmount()).isEqualTo(1000f);
		assertThat(entity.getQuantity()).isEqualTo(10);
		assertThat(entity.getCost()).isEqualTo(100f);
	}

}
