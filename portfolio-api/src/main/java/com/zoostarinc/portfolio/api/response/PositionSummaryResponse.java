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
public class PositionSummaryResponse {

	private Float amount = 0.0f;
	
	private Integer quantity = 0;
	
	private Float cost;
	
}
