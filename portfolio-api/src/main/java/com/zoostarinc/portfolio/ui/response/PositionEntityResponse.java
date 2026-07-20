package com.zoostarinc.portfolio.ui.response;

import com.zoostarinc.portfolio.ui.request.GenericPositionTransactionRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PositionEntityResponse extends GenericPositionTransactionRequest {

	private String positionId;
	
}
