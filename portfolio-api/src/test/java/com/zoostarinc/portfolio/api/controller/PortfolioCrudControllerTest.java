package com.zoostarinc.portfolio.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zoostarinc.portfolio.AbstractMockRepository;
import com.zoostarinc.portfolio.api.response.PositionDetailResponse;
import com.zoostarinc.portfolio.api.response.PositionResponse;
import com.zoostarinc.portfolio.api.response.PositionSummary;
import com.zoostarinc.portfolio.api.response.PositionSummaryResponse;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class PortfolioCrudControllerTest extends AbstractMockRepository {

	@Test
	void testSummary200() throws Exception {
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
	void testTickerSummary200() throws Exception {
		// given
		var url = "/summary";
		String tickerLabel = "ticker";
		String tickerValue = "JUNIT";

		// mock
		List<PositionEntity> entities = new ArrayList<>(1);
		entities.add(new PositionEntity("1", oidcUser().getSubject(), tickerValue, LocalDate.now(), 10, 1000f));
		when(positionRepository.findByUserIdAndTickerOrderByTickerAscQuantityDesc(oidcUser().getSubject(), tickerValue))
				.thenReturn(entities);

		// when
		var response = getJsonResponse(url, tickerLabel, tickerValue);

		// then
		assertThat(response.getStatus()).isEqualTo(200);
		var result = om.readValue(response.getContentAsString(), PositionSummaryResponse.class);
		assertThat(result.getPositions()).hasSize(1);
		PositionSummary entity = result.getPositions().get(0);
		assertThat(entity.getAmount()).isEqualTo(1000f);
		assertThat(entity.getQuantity()).isEqualTo(10);
		assertThat(entity.getCost()).isEqualTo(100f);
	}

	@Test
	void testDetail200() throws Exception {
		// given
		var url = "/detail";

		// mock
		List<PositionEntity> entities = new ArrayList<>(5);
		var entity1 = new PositionEntity("1", oidcUser().getSubject(), "JUNIT1", LocalDate.now(), 100, 15000f);
		entities.add(entity1);
		var entity2 = new PositionEntity("2", oidcUser().getSubject(), "JUNIT1", LocalDate.now(), 100, 16000f);
		entities.add(entity2);
		entities.add(new PositionEntity("3", oidcUser().getSubject(), "JUNIT3", LocalDate.now(), 100, 1250f));
		entities.add(new PositionEntity("4", oidcUser().getSubject(), "JUNIT3", LocalDate.now(), 100, 1200f));
		entities.add(new PositionEntity("5", oidcUser().getSubject(), "JUNIT5", LocalDate.now(), 100, 200.50f));

		when(positionRepository.findByUserIdOrderByTickerAscQuantityDesc(oidcUser().getSubject())).thenReturn(entities);

		// when
		var response = getJsonResponse(url);

		// then
		assertThat(response.getStatus()).isEqualTo(200);
		Map<String, List<PositionDetailResponse>> results = om.readValue(response.getContentAsString(),
				new TypeReference<Map<String, List<PositionDetailResponse>>>() {
				});
		assertThat(results).hasSize(3);

		List<PositionDetailResponse> result = results.get("JUNIT1");
		assertThat(result).hasSize(2);

		var position = result.get(0);
		PositionEntity actualEntity = new PositionEntity(position.getPositionId(), oidcUser().getSubject(), "JUNIT1",
				position.getDate(), position.getQuantity(), position.getAmount());
		assertThat(actualEntity).isEqualTo(entity1).hasSameHashCodeAs(entity1).isNotEqualTo(entity2);
	}

	@Test
	void testUpdate200() throws Exception {
		// given
		String url = "/update";
		var request = new PositionResponse("1");
		request.setAmount(8000f);
		request.setDate(LocalDate.now());
		request.setQuantity(100);
		request.setTicker("JUNIT");

		// mock
		var entity = new PositionEntity();
		entity.setAmount(10000f);
		entity.setDate(request.getDate());
		entity.setId(request.getPositionId());
		entity.setQuantity(request.getQuantity());
		entity.setTicker(request.getTicker());
		entity.setUserId(oidcUser().getSubject());
		when(positionRepository.findByIdAndUserId(request.getPositionId(), oidcUser().getSubject()))
				.thenReturn(Optional.of(entity));
		when(positionRepository.save(entity)).thenReturn(entity);

		// when
		var response = postJsonRequest(url, request);

		// then
		assertThat(response.getStatus()).isEqualTo(200);
		var result = om.readValue(response.getContentAsString(), PositionResponse.class);
		assertThat(result.getAmount()).isEqualTo(request.getAmount());
	}

	@Test
	void testUpdate400MissingPosition() throws Exception {
		// given
		String url = "/update";
		var request = new PositionResponse("1");
		request.setAmount(8000f);
		request.setDate(LocalDate.now());
		request.setQuantity(100);
		request.setTicker("JUNIT");

		// when
		var response = postJsonRequest(url, request);

		// then
		assertThat(response.getStatus()).isEqualTo(400);
		String message = response.getContentAsString();
		log.info("Response: {}", message);
		assertThat(message).contains("Position not found!");
	}

	@Test
	void testUpdate400NullTicker() throws Exception {
		// given
		String url = "/update";
		var request = new PositionResponse("1");
		request.setAmount(8000f);
		request.setDate(LocalDate.now());
		request.setQuantity(100);

		// when
		var response = postJsonRequest(url, request);

		// then
		assertThat(response.getStatus()).isEqualTo(400);
		String message = response.getContentAsString();
		log.info("Response: {}", message);
		assertThat(message).contains("Ticker must not be empty!");
	}

	@Test
	void testUpdate400EmptyTicker() throws Exception {
		// given
		String url = "/update";
		var request = new PositionResponse("1");
		request.setAmount(8000f);
		request.setDate(LocalDate.now());
		request.setQuantity(100);
		request.setTicker("");

		// when
		var response = postJsonRequest(url, request);

		// then
		assertThat(response.getStatus()).isEqualTo(400);
		String message = response.getContentAsString();
		log.info("Response: {}", message);
		assertThat(message).contains("Ticker must not be empty!");
	}

	@Test
	void testDelete200() throws Exception {
		// given
		String url = "/delete";
		String positionId = "1";

		// mock
		var entity = new PositionEntity();
		entity.setAmount(10000f);
		entity.setDate(LocalDate.now());
		entity.setId(positionId);
		entity.setQuantity(100);
		entity.setTicker("JUNIT");
		entity.setUserId(oidcUser().getSubject());
		when(positionRepository.findByIdAndUserId(positionId, oidcUser().getSubject())).thenReturn(Optional.of(entity));
		when(positionRepository.findByUserIdAndTickerOrderByTickerAscQuantityDesc(oidcUser().getSubject(), ""))
				.thenReturn(Collections.emptyList());

		// when
		var response = deleteJsonRequest(url, "positionId", positionId);

		// then
		assertThat(response.getStatus()).isEqualTo(200);
		Map<String, List<PositionDetailResponse>> results = om.readValue(response.getContentAsString(),
				new TypeReference<Map<String, List<PositionDetailResponse>>>() {
				});
		assertThat(results).isEmpty();
	}

	@Test
	void testDelete400() throws Exception {
		// given
		String url = "/delete";
		String positionId = "1";

		// mock
		when(positionRepository.findByIdAndUserId(positionId, oidcUser().getSubject()))
				.thenReturn(Optional.ofNullable(null));

		// when
		var response = deleteJsonRequest(url, "positionId", positionId);

		// then
		assertThat(response.getStatus()).isEqualTo(400);
		String message = response.getContentAsString();
		log.info("Response: {}", message);
		assertThat(message).contains("Position not found!");
	}

}
