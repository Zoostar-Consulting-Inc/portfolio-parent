package com.zoostarinc.portfolio.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.dao.repository.PositionRepository;
import com.zoostarinc.portfolio.model.Position;
import com.zoostarinc.portfolio.service.PortfolioManager;
import com.zoostarinc.portfolio.util.function.PositionSupplier;
import com.zoostarinc.portfolio.validation.TickerRequestValidator;

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
	public Position create(Supplier<PositionEntity> supplier) {
		return positionRepository.save(supplier.get());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Position> retrievePositionSummaryByTickerForUser(String userId, String ticker) {
		if (StringUtils.hasText(ticker)) {
			return new PositionSupplier(positionRepository.findByUserIdAndTickerOrderByTickerAscQuantityDesc(userId,
					TickerRequestValidator.INSTANCE.apply(ticker))).get();
		} else {
			return new PositionSupplier(positionRepository.findByUserIdOrderByTickerAscQuantityDesc(userId)).get();
		}
	}

	@Override
	public Position update(Supplier<PositionEntity> supplier) {
		var position = supplier.get();
		var entity = positionRepository.findById(position.getId())
				.orElseThrow(() -> new IllegalArgumentException("Position not found"));
		entity.setAmount(position.getAmount());
		entity.setQuantity(position.getQuantity());
		entity.setTicker(position.getTicker());
		entity.setDate(position.getDate());
		entity.setUserId(position.getUserId());
		entity.setLastUpdated(Instant.now());
		return positionRepository.save(entity);
	}

	@Override
	public List<Position> delete(String userId, String positionId) {
		var entity = positionRepository.findById(positionId);
		if (entity.isPresent()) {
			log.info("Delete requested by {}: {}...", userId, entity);
			positionRepository.deleteByUserIdAndId(userId, positionId);
		} else {
			log.warn("Delete requested by {}: {} not found.", userId, positionId);
		}
		return new PositionSupplier(
				positionRepository.findByUserIdAndTickerOrderByTickerAscQuantityDesc(userId, entity.get().getTicker()))
				.get();
	}

}
