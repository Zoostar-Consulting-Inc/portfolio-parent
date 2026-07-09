package com.zoostarinc.portfolio.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PortfolioSummary {

	private String ticker;
	
	private Integer quantity;
	
	private Float amount;
	
}
