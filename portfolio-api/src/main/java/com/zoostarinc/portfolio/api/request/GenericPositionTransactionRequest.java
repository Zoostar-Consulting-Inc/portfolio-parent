package com.zoostarinc.portfolio.api.request;

import java.util.Date;

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
public class GenericPositionTransactionRequest {
	
	private Date txDate;
	
	private String ticker;
	
	private Integer quantity;
	
	private Float amount;

}
