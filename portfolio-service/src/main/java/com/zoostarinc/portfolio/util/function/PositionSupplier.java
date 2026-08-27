package com.zoostarinc.portfolio.util.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.util.CollectionUtils;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.model.Position;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PositionSupplier implements Supplier<List<Position>> {

	private final List<PositionEntity> entities;
	
	@Override
	public List<Position> get() {
		if(CollectionUtils.isEmpty(entities)) {
			return Collections.emptyList();
		}
		
		List<Position> positions = new ArrayList<>(entities.size());
		for(PositionEntity entity : entities) {
			positions.add(entity);
		}
		
		return positions;
	}

}
