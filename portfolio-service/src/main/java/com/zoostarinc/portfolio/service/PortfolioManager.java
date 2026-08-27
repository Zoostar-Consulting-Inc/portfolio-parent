package com.zoostarinc.portfolio.service;

import java.util.List;
import java.util.function.Supplier;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.model.Position;

public interface PortfolioManager {

	Position create(Supplier<PositionEntity> supplier);

	List<Position> retrievePositionSummaryByTickerForUser(String oauthUserId, String ticker);
	
	Position update(Supplier<PositionEntity> supplier);

	List<Position> delete(String oauthUserId, String positionId);

	
}
