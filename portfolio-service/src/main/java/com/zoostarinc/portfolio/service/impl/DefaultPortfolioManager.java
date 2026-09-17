package com.zoostarinc.portfolio.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
	public List<PositionEntity> retrievePositionSummaryByTickerForUser(String userId, String ticker) {
		if (StringUtils.hasText(ticker)) {
			return positionRepository.findByUserIdAndTickerOrderByTickerAscQuantityDesc(userId, ticker.toUpperCase());
		} else {
			return positionRepository.findByUserIdOrderByTickerAscQuantityDesc(userId);
		}
	}

	@Override
	public PositionEntity update(Supplier<PositionEntity> supplier) {
		var position = supplier.get();
		log.debug("Id: ", position.getId());
		log.debug("User Id: {}", position.getUserId());
		var entity = positionRepository.findByIdAndUserId(position.getId(), position.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("Position not found!"));
		log.info("Found entity for given Id and user: {}", entity);
		entity.setAmount(position.getAmount());
		entity.setQuantity(position.getQuantity());
		entity.setTicker(position.getTicker());
		entity.setDate(position.getDate());
		entity.setLastUpdated(Instant.now());
		return positionRepository.save(entity);
	}

	@Override
	public List<PositionEntity> delete(String userId, String positionId) {
		var entity = positionRepository.findByIdAndUserId(positionId, userId);
		if (entity.isPresent()) {
			log.info("Delete requested by {}: {}...", userId, entity);
			positionRepository.deleteByUserIdAndId(userId, positionId);
		} else {
			log.warn("Delete requested by {}: {} not found.", userId, positionId);
			throw new IllegalArgumentException("Position not found!");
		}
		return positionRepository.findByUserIdAndTickerOrderByTickerAscQuantityDesc(userId, entity.get().getTicker());
	}

}
