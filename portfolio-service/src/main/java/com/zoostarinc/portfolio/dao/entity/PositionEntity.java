package com.zoostarinc.portfolio.dao.entity;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "positions")
public class PositionEntity {

	@Id
	private String id;
	
	private String oauthUserId;
	
	private Date txDate;
	
	private String ticker;
	
	private Integer quantity;
	
	private Float amount;
	
	private Date lastUpdated;

	public PositionEntity(String oauthUserId, Date txDate, String ticker, Integer quantity, Float amount) {
		this.oauthUserId = oauthUserId;
		this.txDate = txDate;
		this.ticker = ticker;
		this.quantity = quantity;
		this.amount = amount;
		this.lastUpdated = new Date();
	}

	@Override
	public int hashCode() {
		return Objects.hash(quantity, ticker, txDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PositionEntity)) {
			return false;
		}
		PositionEntity other = (PositionEntity) obj;
		return Objects.equals(quantity, other.quantity) && Objects.equals(ticker, other.ticker)
				&& Objects.equals(txDate, other.txDate);
	}
	
}
