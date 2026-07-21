package com.zoostarinc.portfolio.model;

import java.util.Date;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PositionDetail {

	public PositionDetail(PositionEntity entity) {
		this.id = entity.getId();
		this.txDate = entity.getTxDate();
		this.quantity = entity.getQuantity();
		this.amount = entity.getAmount();
	}
	
	private String id;
	
	private Date txDate;
	
	private Integer quantity;
	
	private Float amount;
	
}
