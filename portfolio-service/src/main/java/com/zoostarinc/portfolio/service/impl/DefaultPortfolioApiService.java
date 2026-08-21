package com.zoostarinc.portfolio.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import com.zoostarinc.portfolio.api.response.PortfolioSummaryResponse;
import com.zoostarinc.portfolio.service.PortfolioApiService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Service
@RequiredArgsConstructor
public class DefaultPortfolioApiService implements PortfolioApiService {

	private final RestClient restClient;

	@Override
	public PortfolioSummaryResponse getPortfolioSummary(String token, String ticker) {
		StringBuilder url = new StringBuilder("https://portfolio.apigator.net/summary");
		if (StringUtils.hasText(ticker)) {
			url.append("?ticker=").append(ticker);
		}

		var response = restClient.get().uri(url.toString()).retrieve().
				toEntity(PortfolioSummaryResponse.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new RuntimeException("Failed to retrieve portfolio summary: " + response.getStatusCode());
		}
	}

}
