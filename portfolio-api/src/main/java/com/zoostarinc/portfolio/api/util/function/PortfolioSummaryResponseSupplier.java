package com.zoostarinc.portfolio.api.util.function;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;

import com.zoostarinc.portfolio.api.response.PortfolioSummaryResponse;
import com.zoostarinc.portfolio.api.response.PositionSummaryResponse;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@RequiredArgsConstructor
public class PortfolioSummaryResponseSupplier implements Supplier<PortfolioSummaryResponse> {

	private final List<PositionEntity> positions;
	
	@Override
	public PortfolioSummaryResponse get() {
		log.info("Processing response for {} position(s)...", positions.size());
		var positionSummaryByTicker = new LinkedHashMap<String, PositionSummaryResponse>();
		var response = new PortfolioSummaryResponse();
		response.setPositions(positionSummaryByTicker);
		log.info("Summarizing {} position(s) by ticker...", positions.size());
		for(var position : positions) {
			var positionSummary = positionSummaryByTicker.computeIfAbsent(position.getTicker(), k -> new PositionSummaryResponse());
			positionSummary.setAmount(positionSummary.getAmount() + position.getAmount());
			positionSummary.setQuantity(positionSummary.getQuantity() + position.getQuantity());
			if(positionSummary.getQuantity() > 0) {
				positionSummary.setCost(positionSummary.getAmount() / positionSummary.getQuantity());
				positionSummaryByTicker.put(position.getTicker(), positionSummary);
			} else {
				positionSummaryByTicker.remove(position.getTicker());
			}
		}
		
		return response;
	}

}
