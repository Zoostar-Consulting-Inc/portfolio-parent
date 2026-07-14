package com.zoostarinc.portfolio.service;

import java.util.List;
import java.util.function.Supplier;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;

public interface PortfolioManager {

	PositionEntity create(Supplier<PositionEntity> supplier);
	
	List<PositionEntity> retrievePositionSummaryByTickerForUser(String oauthUserId);
	
}
