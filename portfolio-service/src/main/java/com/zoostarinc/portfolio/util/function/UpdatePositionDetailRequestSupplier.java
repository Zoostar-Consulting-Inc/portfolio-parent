package com.zoostarinc.portfolio.util.function;

import java.util.function.Supplier;

import com.nimbusds.jwt.util.DateUtils;
import com.zoostarinc.portfolio.api.response.PositionResponse;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.validation.TickerRequestValidator;

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
		var entity = new PositionEntity();
		entity.setTicker(TickerRequestValidator.INSTANCE.apply(request.getTicker()));
		entity.setId(request.getPositionId());
		entity.setAmount(request.getAmount());
		entity.setQuantity(request.getQuantity());
		entity.setTxDate(request.getTxDate());
		entity.setOauthUserId(oauthUserId);
		entity.setLastUpdated(DateUtils.nowWithSecondsPrecision());
		return entity;
	}

}
