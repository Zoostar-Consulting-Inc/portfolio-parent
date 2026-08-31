package com.zoostarinc.portfolio.api.response;

import java.time.LocalDate;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;

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
	
	private String positionId;
	
	private LocalDate date;
	
	private Integer quantity;
	
	private Float amount;

	public PositionDetailResponse(PositionEntity entity) {
		this.positionId = entity.getId();
		this.date = entity.getDate();
		this.quantity = entity.getQuantity();
		this.amount = entity.getAmount();
	}
	
}
