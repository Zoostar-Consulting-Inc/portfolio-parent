package com.zoostarinc.portfolio.service.impl;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.dao.repository.PositionRepository;
import com.zoostarinc.portfolio.service.PortfolioManager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultPortfolioManager implements PortfolioManager {

	private final PositionRepository positionRepository;

	@Override
	public PositionEntity create(Supplier<PositionEntity> supplier) {
		return positionRepository.save(supplier.get());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PositionEntity> retrievePositionsByUser(String oauthUserId) {
		return positionRepository.findByOauthUserIdOrderByTickerAscQuantityDesc(oauthUserId);
	}

}
