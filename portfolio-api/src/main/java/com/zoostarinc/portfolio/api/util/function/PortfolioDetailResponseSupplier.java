package com.zoostarinc.portfolio.api.util.function;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.zoostarinc.portfolio.api.response.PositionDetailResponse;
import com.zoostarinc.portfolio.model.Position;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class PortfolioDetailResponseSupplier implements Supplier<Map<String, List<PositionDetailResponse>>> {

	private final List<Position> entities;

	@Override
	public Map<String, List<PositionDetailResponse>> get() {
		log.info("Processing response for {} entities...", entities.size());
		var positionDetailByTicker = new LinkedHashMap<String, List<PositionDetailResponse>>();
		for(var entity : entities) {
			var positions = positionDetailByTicker.computeIfAbsent(entity.getTicker(), k -> new ArrayList<>());
			var response = new PositionDetailResponse(entity);
			positions.add(response);
			positionDetailByTicker.put(entity.getTicker(), positions);
		}
		
		return positionDetailByTicker;
	}

}
