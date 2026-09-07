package com.zoostarinc.portfolio.api.response;

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
public class PositionSummary {
	
	private String ticker;
	
	private Integer quantity = 0;

	private Float amount = 0.0f;
	
	private Float cost;
	
}
