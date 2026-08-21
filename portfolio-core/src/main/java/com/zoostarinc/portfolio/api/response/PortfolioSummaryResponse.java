package com.zoostarinc.portfolio.api.response;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PortfolioSummaryResponse {
	private Map<String, PositionSummaryResponse> positions;
}
