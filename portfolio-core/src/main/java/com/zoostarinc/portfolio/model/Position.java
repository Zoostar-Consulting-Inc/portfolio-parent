package com.zoostarinc.portfolio.model;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Position {

	private String id;
	
	private String userId;
	
	private Instant date;
	
	private String ticker;
	
	private Integer quantity;
	
	private Float amount;
	
	private Instant lastUpdated;

}
