package com.zoostarinc.portfolio.util.function;

import java.util.function.Supplier;

import com.nimbusds.jwt.util.DateUtils;
import com.zoostarinc.portfolio.api.request.PositionRequest;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.validation.TickerRequestValidator;

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
		var position = new PositionEntity();
		position.setTicker(TickerRequestValidator.INSTANCE.apply(request.getTicker()));
		if(request.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero!");
		}
		position.setQuantity(request.getQuantity() * factor);
		position.setAmount(request.getAmount() * factor);
		position.setOauthUserId(oauthUserId);
		position.setTxDate(request.getTxDate());
		position.setLastUpdated(DateUtils.nowWithSecondsPrecision());
		log.debug("Returning persistable entity: {}...", position);
		return position;
	}

}
