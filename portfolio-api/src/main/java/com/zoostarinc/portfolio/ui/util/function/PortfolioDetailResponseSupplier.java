package com.zoostarinc.portfolio.ui.util.function;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.util.CollectionUtils;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.ui.response.PositionEntityResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class PortfolioDetailResponseSupplier implements Supplier<Map<String, List<PositionEntityResponse>>> {

	private final List<PositionEntity> entities;

	@Override
	public Map<String, List<PositionEntityResponse>> get() {
		log.info("Processing response for {} entities...", entities.size());
		var positionDetailByTicker = new LinkedHashMap<String, List<PositionEntityResponse>>();
		for(var entity : entities) {
			var positions = positionDetailByTicker.get(entity.getTicker());
			if(CollectionUtils.isEmpty(positions)) {
				log.debug("Creating new list of positions for ticker: {}", entity.getTicker());
				positions = new ArrayList<>();
			}
			
			var response = new PositionEntityResponse();
			response.setAmount(entity.getAmount());
			response.setPositionId(entity.getId());
			response.setQuantity(entity.getQuantity());
			response.setTicker(entity.getTicker());
			response.setTxDate(entity.getTxDate());
			positions.add(response);
			positionDetailByTicker.put(entity.getTicker(), positions);
		}
		
		return positionDetailByTicker;
	}

}
