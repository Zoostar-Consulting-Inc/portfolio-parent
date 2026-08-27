package com.zoostarinc.portfolio.api.response;

import java.time.Instant;

import com.zoostarinc.portfolio.model.Position;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@ToString
@NoArgsConstructor
public class PositionDetailResponse {

	public PositionDetailResponse(Position position) {
		this.positionId = position.getId();
		this.date = position.getDate();
		this.quantity = position.getQuantity();
		this.amount = position.getAmount();
	}
	
	private String positionId;
	
	private Instant date;
	
	private Integer quantity;
	
	private Float amount;
	
}
