package com.zoostarinc.portfolio.util.function;

import java.time.Instant;
import java.util.function.Supplier;

import org.springframework.util.StringUtils;

import com.zoostarinc.portfolio.api.request.PositionRequest;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@RequiredArgsConstructor
public class PositionRequestEntitySupplier implements Supplier<PositionEntity> {

	private final String oauthUserId;

	private final int factor;

	private final PositionRequest request;

	@Override
	public PositionEntity get() {
		if(request == null || !StringUtils.hasText(request.getTicker())) {
			throw new IllegalArgumentException("Ticker must not be empty!");
		}
		
		var position = new PositionEntity();
		position.setTicker(request.getTicker().toUpperCase());
		
		if (request.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero!");
		}
		position.setQuantity(request.getQuantity() * factor);
		position.setAmount(request.getAmount() * factor);
		position.setUserId(oauthUserId);
		position.setDate(request.getDate());
		position.setLastUpdated(Instant.now());
		log.debug("Created Position from request: {}.", position);
		return position;
	}

}
