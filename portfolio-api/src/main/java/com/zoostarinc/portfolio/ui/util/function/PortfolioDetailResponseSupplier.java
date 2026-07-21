package com.zoostarinc.portfolio.ui.util.function;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.model.PositionDetail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class PortfolioDetailResponseSupplier implements Supplier<Map<String, List<PositionDetail>>> {

	private final List<PositionEntity> entities;

	@Override
	public Map<String, List<PositionDetail>> get() {
		log.info("Processing response for {} entities...", entities.size());
		var positionDetailByTicker = new LinkedHashMap<String, List<PositionDetail>>();
		for(var entity : entities) {
			var positions = positionDetailByTicker.computeIfAbsent(entity.getTicker(), k -> new ArrayList<>());
			var response = new PositionDetail();
			response.setAmount(entity.getAmount());
			response.setId(entity.getId());
			response.setQuantity(entity.getQuantity());
			response.setTxDate(entity.getTxDate());
			positions.add(response);
			positionDetailByTicker.put(entity.getTicker(), positions);
		}
		
		return positionDetailByTicker;
	}

}
