package com.zoostarinc.portfolio.util.function;

import java.time.Instant;
import java.util.function.Supplier;

import org.springframework.util.StringUtils;

import com.zoostarinc.portfolio.api.response.PositionResponse;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdatePositionDetailRequestSupplier implements Supplier<PositionEntity> {

	private final String oauthUserId;
	
	private final PositionResponse request;

	@Override
	public PositionEntity get() {
		if(request == null || !StringUtils.hasText(request.getTicker())) {
			throw new IllegalArgumentException("Ticker must not be empty!");
		}

		var entity = new PositionEntity();
		entity.setTicker(request.getTicker().toUpperCase());
		entity.setId(request.getPositionId());
		entity.setAmount(request.getAmount());
		entity.setQuantity(request.getQuantity());
		entity.setDate(request.getDate());
		entity.setUserId(oauthUserId);
		entity.setLastUpdated(Instant.now());
		return entity;
	}

}
