package com.zoostarinc.portfolio.ui.util.function;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.model.PositionSummary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class PortfolioSummaryResponseSupplier implements Supplier<Map<String, PositionSummary>> {

	private final List<PositionEntity> positions;
	
	@Override
	public Map<String, PositionSummary> get() {
		log.info("Processing response for {} position(s)...", positions.size());
		var positionSummaryByTicker = new LinkedHashMap<String, PositionSummary>();
		log.info("Summarizing {} position(s) by ticker...", positions.size());
		for(var position : positions) {
			var positionSummary = positionSummaryByTicker.computeIfAbsent(position.getTicker(), k -> new PositionSummary());
			positionSummary.setAmount(positionSummary.getAmount() + position.getAmount());
			positionSummary.setQuantity(positionSummary.getQuantity() + position.getQuantity());
			if(positionSummary.getQuantity() > 0) {
				positionSummary.setCost(positionSummary.getAmount() / positionSummary.getQuantity());
				positionSummaryByTicker.put(position.getTicker(), positionSummary);
			} else {
				positionSummaryByTicker.remove(position.getTicker());
			}
		}
		
		return positionSummaryByTicker;
	}

}
