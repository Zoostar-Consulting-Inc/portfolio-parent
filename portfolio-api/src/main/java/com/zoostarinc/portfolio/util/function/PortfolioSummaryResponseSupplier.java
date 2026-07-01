package com.zoostarinc.portfolio.util.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.zoostarinc.portfolio.model.Position;
import com.zoostarinc.portfolio.model.PositionSummary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class PortfolioSummaryResponseSupplier implements Supplier<Map<String, PositionSummary>> {

	private final List<Position> positions;
	
	@Override
	public Map<String, PositionSummary> get() {
		log.info("Processing response for {} positions...", positions.size());
		var positionSummaryByTicker = new HashMap<String, PositionSummary>();
		for(var position : positions) {
			var positionSummary = positionSummaryByTicker.get(position.getTicker());
			if(positionSummary == null) {
				log.info("Adding new position: {}...", position);
				positionSummary = new PositionSummary();
			}
			positionSummary.setTicker(position.getTicker());
			positionSummary.setAmount(positionSummary.getAmount() + position.getAmount());
			positionSummary.setQuantity(positionSummary.getQuantity() + position.getQuantity());
			if(positionSummary.getQuantity() > 0) {
				positionSummaryByTicker.put(position.getTicker(), positionSummary);
				log.info("New position added: {}.", position);
			} else {
				positionSummaryByTicker.remove(position.getTicker());
				log.info("Position removed as quantity reached 0: {}.", position);
			}
		}
		return positionSummaryByTicker;
	}

}
