package com.zoostarinc.portfolio.ui.util.function;

import java.util.function.Supplier;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.StringUtils;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.ui.request.AbstractPositionTransactionRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@RequiredArgsConstructor
public class GenericPositionTransactionRequestSupplier implements Supplier<PositionEntity> {

	private final OidcUser user;
	
	private final int factor;
	
	private final AbstractPositionTransactionRequest request;
	
	@Override
	public PositionEntity get() {
		String ticker = request.getTicker();
		if (!StringUtils.hasText(ticker)) {
			throw new IllegalArgumentException("Ticker cannot be null or empty.");
		}

		var entity = new PositionEntity();
		entity.setTicker(ticker.trim().toUpperCase());
		entity.setAmount(request.getAmount() * factor);
		entity.setOauthUserId(user.getSubject());
		entity.setQuantity(request.getQuantity() * factor);
		entity.setTxDate(request.getTxDate());
		log.debug("Returning persistable entity: {}...", entity);
		return entity;
	}

}
