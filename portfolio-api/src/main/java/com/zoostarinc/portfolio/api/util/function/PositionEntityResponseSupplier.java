package com.zoostarinc.portfolio.api.util.function;

import java.util.function.Supplier;

import com.zoostarinc.portfolio.api.response.PositionResponse;
import com.zoostarinc.portfolio.model.Position;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@RequiredArgsConstructor
public class PositionEntityResponseSupplier implements Supplier<PositionResponse> {

	private final Position entity;

	@Override
	public PositionResponse get() {
		var response = new PositionResponse();
		response.setAmount(entity.getAmount());
		response.setPositionId(entity.getId());
		response.setQuantity(entity.getQuantity());
		response.setTicker(entity.getTicker());
		response.setDate(entity.getDate());
		log.debug("Returning persistent entity: {}", response);
		return response;
	}

}
