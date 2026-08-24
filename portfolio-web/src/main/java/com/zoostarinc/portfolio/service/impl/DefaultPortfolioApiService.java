package com.zoostarinc.portfolio.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import com.zoostarinc.portfolio.api.response.PortfolioSummaryResponse;
import com.zoostarinc.portfolio.service.PortfolioApiService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class DefaultPortfolioApiService implements PortfolioApiService {

	private final RestClient restClient;

	@Override
	public PortfolioSummaryResponse getPortfolioSummary(String sessionId, String token, String ticker) {
		StringBuilder url = new StringBuilder("https://portfolio.apigator.net/summary");
		if (StringUtils.hasText(ticker)) {
			url.append("?ticker=").append(ticker);
		}

		log.debug("Session ID: {}", sessionId);
		log.debug("Authorization Bearer Token: {}", token);
		var response = restClient.get().uri("/summary").
				header("Cookie", "JSESSIONID=" + sessionId).
				header("Authorization", "Bearer " + token).
				retrieve().toEntity(PortfolioSummaryResponse.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new RuntimeException("Failed to retrieve portfolio summary: " + response.getStatusCode());
		}
	}

}
