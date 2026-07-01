package com.zoostarinc.portfolio.service;

import java.util.List;

import com.zoostarinc.portfolio.model.Position;

public interface PortfolioManager {

	List<Position> retrievePositionsByUser(String oauthId);
	
}
