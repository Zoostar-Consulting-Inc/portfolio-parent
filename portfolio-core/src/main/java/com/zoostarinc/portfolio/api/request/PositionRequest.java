package com.zoostarinc.portfolio.api.request;

import java.time.Instant;

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
public class PositionRequest {
	
	private Instant date;
	
	private String ticker;
	
	private Integer quantity;
	
	private Float amount;

}
