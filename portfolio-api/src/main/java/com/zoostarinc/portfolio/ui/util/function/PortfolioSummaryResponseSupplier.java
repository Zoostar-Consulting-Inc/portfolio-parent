package com.zoostarinc.portfolio.ui.util.function;

import java.util.HashMap;
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
		var positionSummaryByTicker = new HashMap<String, PositionSummary>();
		for(var position : positions) {
			var positionSummary = positionSummaryByTicker.get(position.getTicker());
			if(positionSummary == null) {
				log.debug("Adding new position: {}...", position);
				positionSummary = new PositionSummary();
			}
			positionSummary.setAmount(positionSummary.getAmount() + position.getAmount());
			positionSummary.setQuantity(positionSummary.getQuantity() + position.getQuantity());
			if(positionSummary.getQuantity() > 0) {
				positionSummary.setCost(positionSummary.getAmount() / positionSummary.getQuantity());
				positionSummaryByTicker.put(position.getTicker(), positionSummary);
				log.debug("New position added: {}.", position);
			} else {
				positionSummaryByTicker.remove(position.getTicker());
				log.info("Position removed as quantity reached 0: {}.", position);
			}
		}
		
		return positionSummaryByTicker;
	}

}
