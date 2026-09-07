package com.zoostarinc.portfolio.dao.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "positions")
public class PositionEntity {

	@Id
	private String id;
	
	private String userId;
	
	private String ticker;

	private LocalDate date;
	
	private Integer quantity;
	
	private Float amount;
	
	private Instant lastUpdated;
	
	public PositionEntity(String id, String userId, String ticker, LocalDate date, Integer quantity, Float amount) {
		this.id = id;
		this.userId = userId;
		this.ticker = ticker;
		this.date = date;
		this.quantity = quantity;
		this.amount = amount;
		setLastUpdated(Instant.now());
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, date, quantity, ticker, userId);
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
		return Objects.equals(amount, other.amount) && Objects.equals(date, other.date)
				&& Objects.equals(quantity, other.quantity) && Objects.equals(ticker, other.ticker)
				&& Objects.equals(userId, other.userId);
	}

}
