package com.zoostarinc.portfolio.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.portfolio.api.request.GenericPositionTransactionRequest;
import com.zoostarinc.portfolio.api.response.PositionEntityResponse;
import com.zoostarinc.portfolio.api.util.function.GenericPositionTransactionRequestSupplier;
import com.zoostarinc.portfolio.api.util.function.PositionEntityResponseSupplier;
import com.zoostarinc.portfolio.service.PortfolioManager;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PortfolioTransactionController {
	
	private final PortfolioManager defaultPostfolioManager;
	
	@PostMapping(path = "/buy", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionEntityResponse> buy(@AuthenticationPrincipal OidcUser user, @RequestBody GenericPositionTransactionRequest request) {
		return transact(user, request, 1);
	}

	@PostMapping(path = "/sell", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionEntityResponse> sell(@AuthenticationPrincipal OidcUser user, @RequestBody GenericPositionTransactionRequest request) {
		return transact(user, request, -1);
	}
	
	protected ResponseEntity<PositionEntityResponse> transact(OidcUser user, GenericPositionTransactionRequest request, int factor) {
		return ResponseEntity.ok(new PositionEntityResponseSupplier(defaultPostfolioManager.create(new GenericPositionTransactionRequestSupplier(user, factor, request))).get());
	}

}
