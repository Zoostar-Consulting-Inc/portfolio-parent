package com.zoostarinc.portfolio.api.response;

import java.util.Date;

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

	public PositionDetailResponse(PositionEntity entity) {
		this.positionId = entity.getId();
		this.txDate = entity.getTxDate();
		this.quantity = entity.getQuantity();
		this.amount = entity.getAmount();
	}
	
	private String positionId;
	
	private Date txDate;
	
	private Integer quantity;
	
	private Float amount;
	
}
