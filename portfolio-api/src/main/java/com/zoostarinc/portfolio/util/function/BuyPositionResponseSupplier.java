package com.zoostarinc.portfolio.util.function;

import java.util.function.Supplier;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.ui.response.PositionEntityResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@RequiredArgsConstructor
public class BuyPositionResponseSupplier implements Supplier<PositionEntityResponse> {

	private final PositionEntity entity;

	@Override
	public PositionEntityResponse get() {
		var response = new PositionEntityResponse();
		response.setAmount(entity.getAmount());
		response.setPositionId(entity.getId());
		response.setQuantity(entity.getQuantity());
		response.setTicker(entity.getTicker());
		response.setTxDate(entity.getTxDate());
		log.debug("Returning persistent entity: {}", response);
		return response;
	}

}
