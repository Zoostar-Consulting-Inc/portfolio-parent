package com.zoostarinc.portfolio.ui.util.function;

import java.util.function.Supplier;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.nimbusds.jwt.util.DateUtils;
import com.zoostarinc.portfolio.dao.entity.PositionEntity;
import com.zoostarinc.portfolio.ui.response.PositionEntityResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdatePositionDetailRequestSupplier implements Supplier<PositionEntity> {

	private final OidcUser user;
	
	private final PositionEntityResponse request;

	@Override
	public PositionEntity get() {
		var position = new PositionEntity();
		position.setId(request.getPositionId());
		position.setAmount(request.getAmount());
		position.setQuantity(request.getQuantity());
		position.setTicker(request.getTicker());
		position.setTxDate(request.getTxDate());
		position.setOauthUserId(user.getSubject());
		position.setLastUpdated(DateUtils.nowWithSecondsPrecision());
		return position;
	}

}
