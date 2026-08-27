package com.zoostarinc.portfolio.dao.entity;

import java.time.Instant;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zoostarinc.portfolio.model.Position;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document(collection = "positions")
public class PositionEntity extends Position {

	public PositionEntity(String userId, Instant date, String ticker, Integer quantity, Float amount) {
		this(null, userId, date, ticker, quantity, amount);
	}

	public PositionEntity(String id, String userId, Instant date, String ticker, Integer quantity, Float amount) {
		setId(id);
		setUserId(userId);
		setDate(date);
		setTicker(ticker);
		setQuantity(quantity);
		setAmount(amount);
		setLastUpdated(Instant.now());
	}

	@Id
	@Override
	public String getId() {
		return super.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUserId(), getTicker(), getQuantity(), getAmount(), getDate());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PositionEntity)) {
			return false;
		}
		PositionEntity that = (PositionEntity) obj;
		return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getTicker(), that.getTicker())
				&& Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getAmount(), that.getAmount())
				&& Objects.equals(getDate(), that.getDate());
	}

}
