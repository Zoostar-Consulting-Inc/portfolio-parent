package com.zoostarinc.portfolio.service;

import java.util.List;
import java.util.function.Supplier;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;

import net.zoostar.common.StringWrapper;

public interface PortfolioManager {

	PositionEntity create(Supplier<PositionEntity> supplier);

	List<PositionEntity> retrievePositionSummaryByTickerForUser(String oauthUserId, String ticker);
	
	PositionEntity update(Supplier<PositionEntity> supplier);

	List<PositionEntity> delete(String oauthUserId, StringWrapper positionId);
	
}
