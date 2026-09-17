package com.zoostarinc.portfolio.api.util.function;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;

import com.zoostarinc.portfolio.api.response.PositionSummary;
import com.zoostarinc.portfolio.api.response.PositionSummaryResponse;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PositionSummaryResponseSupplier implements Supplier<PositionSummaryResponse> {

	private final List<PositionEntity> positions;

	@Override
	public PositionSummaryResponse get() {
		var positionSummaryByTicker = new LinkedHashMap<String, PositionSummary>();
		for (var position : positions) {
			var positionSummary = positionSummaryByTicker.computeIfAbsent(position.getTicker(),
					k -> new PositionSummary());
			positionSummary.setAmount(positionSummary.getAmount() + position.getAmount());
			positionSummary.setQuantity(positionSummary.getQuantity() + position.getQuantity());
			if (positionSummary.getQuantity() > 0) {
				positionSummary.setCost(positionSummary.getAmount() / positionSummary.getQuantity());
				positionSummaryByTicker.put(position.getTicker(), positionSummary);
			} else {
				positionSummaryByTicker.remove(position.getTicker());
			}
		}

		List<PositionSummary> positionSummaryList = new ArrayList<>(positionSummaryByTicker.size());
		for (var entry : positionSummaryByTicker.entrySet()) {
			var positionSummary = new PositionSummary(entry.getKey(), entry.getValue().getQuantity(),
					entry.getValue().getAmount(), entry.getValue().getCost());
			positionSummaryList.add(positionSummary);
		}
		
		return new PositionSummaryResponse(positionSummaryList);
	}

}
