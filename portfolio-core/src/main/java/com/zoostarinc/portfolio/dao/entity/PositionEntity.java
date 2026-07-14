package com.zoostarinc.portfolio.dao.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Document(collection = "positions")
@ToString
public class PositionEntity {

	@Id
	private String id;
	
	private String oauthUserId;
	
	private Date txDate;
	
	private String ticker;
	
	private Integer quantity;
	
	private Float amount;

}
