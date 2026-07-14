package com.zoostarinc.portfolio.util.function;

import java.util.function.Supplier;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.ui.request.AbstractPositionTransactionRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class BuyPositionRequestSupplier implements Supplier<PositionEntity> {

	private final DefaultOidcUser user;
	
	private final AbstractPositionTransactionRequest request;
	
	@Override
	public PositionEntity get() {
		var entity = new PositionEntity();
		entity.setAmount(request.getAmount());
		entity.setOauthUserId(user.getSubject());
		entity.setQuantity(request.getQuantity());
		entity.setTicker(request.getTicker());
		entity.setTxDate(request.getTxDate());
		log.debug("Returning persistable entity: {}...", entity);
		return entity;
	}

}
