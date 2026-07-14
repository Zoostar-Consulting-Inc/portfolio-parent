package com.zoostarinc.portfolio.ui.request;

import java.util.function.Supplier;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class SellPositionRequestSupplier implements Supplier<PositionEntity> {

	private final DefaultOidcUser user;
	
	private final AbstractPositionTransactionRequest request;
	
	@Override
	public PositionEntity get() {
		var entity = new PositionEntity();
		entity.setAmount(-1 * request.getAmount());
		entity.setOauthUserId(user.getSubject());
		entity.setQuantity(-1 * request.getQuantity());
		entity.setTicker(request.getTicker());
		entity.setTxDate(request.getTxDate());
		log.debug("Returning new entity: {}...", entity);
		return entity;
	}

}
