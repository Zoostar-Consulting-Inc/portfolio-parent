package com.zoostarinc.portfolio.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zoostarinc.portfolio.dao.repository.PortfolioRepository;
import com.zoostarinc.portfolio.model.Position;
import com.zoostarinc.portfolio.service.PortfolioManager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Service
@RequiredArgsConstructor
public class DefaultPortfolioManager implements PortfolioManager {

	private final PortfolioRepository portfolioRepository;
	
	@Override
	public List<Position> retrievePositionsByUser(String oauthId) {
		List<Position> positions = new ArrayList<>();
		positions.add(new Position(new Date(), "AEO", 600, 11300f));
		return positions;
	}

}
