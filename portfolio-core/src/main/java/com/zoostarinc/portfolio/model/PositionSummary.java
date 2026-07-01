package com.zoostarinc.portfolio.model;

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
	
	private Float amount = 0.0f;
	
	private Integer quantity = 0;
	
}
