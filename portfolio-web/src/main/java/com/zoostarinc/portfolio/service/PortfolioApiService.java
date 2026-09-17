package com.zoostarinc.portfolio.service;

import com.zoostarinc.portfolio.api.response.PositionSummaryResponse;

public interface PortfolioApiService {

	PositionSummaryResponse getPortfolioSummary(String ticker);
	
}
