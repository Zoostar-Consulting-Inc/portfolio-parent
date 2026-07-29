package com.zoostarinc.portfolio.api.util.function;

import java.util.function.Supplier;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.nimbusds.jwt.util.DateUtils;
import com.zoostarinc.portfolio.api.response.PositionResponse;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.validation.TickerRequestValidator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdatePositionDetailRequestSupplier implements Supplier<PositionEntity> {

	private final OidcUser user;
	
	private final PositionResponse request;

	@Override
	public PositionEntity get() {
		var entity = new PositionEntity();
		entity.setTicker(TickerRequestValidator.INSTANCE.apply(request.getTicker()));
		entity.setId(request.getPositionId());
		entity.setAmount(request.getAmount());
		entity.setQuantity(request.getQuantity());
		entity.setTxDate(request.getTxDate());
		entity.setOauthUserId(user.getSubject());
		entity.setLastUpdated(DateUtils.nowWithSecondsPrecision());
		return entity;
	}

}
