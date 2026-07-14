package com.zoostarinc.portfolio.ui.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.util.CollectionUtils;

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
		var positionDetailByTicker = new HashMap<String, List<PositionDetail>>();
		for(var entity : entities) {
			var positions = positionDetailByTicker.get(entity.getTicker());
			if(CollectionUtils.isEmpty(positions)) {
				log.debug("Creating new list of positions for ticker: {}", entity.getTicker());
				positions = new ArrayList<>();
			}
			positions.add(new PositionDetail(entity));
			positionDetailByTicker.put(entity.getTicker(), positions);
		}
		
		return positionDetailByTicker;
	}

}
