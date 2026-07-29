package com.zoostarinc.portfolio.api.response;

import com.zoostarinc.portfolio.api.request.PositionRequest;

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
public class PositionResponse extends PositionRequest {

	private String positionId;
	
}
