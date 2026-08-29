package com.zoostarinc.portfolio.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

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

	public static final String SUMMARY_URI = "http://localhost:9080/portfolio-api/summary";

	private final RestClient restClient;

	@Override
	public PortfolioSummaryResponse getPortfolioSummary(String ticker) {
		StringBuilder uri = new StringBuilder(SUMMARY_URI);
		if (StringUtils.hasText(ticker)) {
			uri.append("?ticker=").append(ticker);
		}

		log.info("Making call to Portfolio API: {}...", uri.toString());
		var response = restClient.get().uri(uri.toString()).retrieve().toEntity(PortfolioSummaryResponse.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			if(response.getStatusCode().is4xxClientError()) {
				log.warn("Client error occurred while calling Portfolio API: {}", response.getStatusCode());
			} else if(response.getStatusCode().is5xxServerError()) {
				log.error("Server error occurred while calling Portfolio API: {}", response.getStatusCode());
			} else {
				log.error("Unexpected error occurred while calling Portfolio API: {}", response.getStatusCode());
			}
			throw new RestClientException("Failed to retrieve portfolio summary from Portfolio API: " + response.getStatusCode());
		}
	}

}
