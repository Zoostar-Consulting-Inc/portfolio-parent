package com.zoostarinc.portfolio.service;

import com.zoostarinc.portfolio.api.response.PortfolioSummaryResponse;

public interface PortfolioApiService {

	PortfolioSummaryResponse getPortfolioSummary(String ticker);
	
}
