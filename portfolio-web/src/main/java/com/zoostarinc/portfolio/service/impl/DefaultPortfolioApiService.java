package com.zoostarinc.portfolio.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import com.zoostarinc.portfolio.api.response.PositionSummaryResponse;
import com.zoostarinc.portfolio.service.PortfolioApiService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class DefaultPortfolioApiService implements PortfolioApiService {

	@Value("${base-url.portfolio-api}")
	private String portfolioApiBaseUrl;

	private final RestClient restClient;

	@Override
	public PositionSummaryResponse getPortfolioSummary(String ticker) {
		StringBuilder uri = new StringBuilder(portfolioApiBaseUrl).append("/summary");
		if (StringUtils.hasText(ticker)) {
			uri.append("?ticker=").append(ticker);
		}

		log.info("Making call to Portfolio Summary API: {}...", uri.toString());
		return restClient.get().uri(uri.toString()).retrieve().toEntity(PositionSummaryResponse.class).getBody();
	}

}
