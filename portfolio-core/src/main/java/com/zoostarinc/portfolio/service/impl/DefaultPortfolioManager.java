package com.zoostarinc.portfolio.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.dao.repository.PositionRepository;
import com.zoostarinc.portfolio.service.PortfolioManager;
import com.zoostarinc.portfolio.validation.TickerRequestValidator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.StringWrapper;

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
	public List<PositionEntity> retrievePositionSummaryByTickerForUser(String oauthUserId, String ticker) {
		if (StringUtils.hasText(ticker)) {
			return positionRepository.findByOauthUserIdAndTickerOrderByTickerAscQuantityDesc(oauthUserId,
					TickerRequestValidator.INSTANCE.apply(ticker));
		} else {
			return positionRepository.findByOauthUserIdOrderByTickerAscQuantityDesc(oauthUserId);
		}
	}

	@Override
	public PositionEntity update(Supplier<PositionEntity> supplier) {
		var position = supplier.get();
		var entity = positionRepository.findById(position.getId())
				.orElseThrow(() -> new IllegalArgumentException("Position not found"));
		entity.setAmount(position.getAmount());
		entity.setQuantity(position.getQuantity());
		entity.setTicker(position.getTicker());
		entity.setTxDate(position.getTxDate());
		entity.setOauthUserId(position.getOauthUserId());
		entity.setLastUpdated(new Date());
		return positionRepository.save(entity);
	}

	@Override
	public List<PositionEntity> delete(String oauthUserId, StringWrapper positionId) {
		var entity = positionRepository.findById(positionId.getValue());
		if (entity.isPresent()) {
			log.info("Delete requested by {}: {}...", oauthUserId, entity);
			positionRepository.deleteByOauthUserIdAndId(oauthUserId, positionId.getValue());
		} else {
			log.warn("Delete requested by {}: {} not found.", oauthUserId, positionId.getValue());
		}
		return positionRepository.findByOauthUserIdAndTickerOrderByTickerAscQuantityDesc(oauthUserId,
				entity.get().getTicker());
	}

}
