package com.zoostarinc.portfolio.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.portfolio.api.request.PositionRequest;
import com.zoostarinc.portfolio.api.response.PositionResponse;
import com.zoostarinc.portfolio.api.util.function.PositionEntityResponseSupplier;
import com.zoostarinc.portfolio.service.PortfolioManager;
import com.zoostarinc.portfolio.util.function.PositionRequestEntitySupplier;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PortfolioTransactionController {
	
	private final PortfolioManager defaultPostfolioManager;
	
	@PostMapping(path = "/buy", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionResponse> buy(@AuthenticationPrincipal OidcUser user, @RequestBody PositionRequest request) {
		return transact(user, request, 1);
	}

	@PostMapping(path = "/sell", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionResponse> sell(@AuthenticationPrincipal OidcUser user, @RequestBody PositionRequest request) {
		return transact(user, request, -1);
	}
	
	protected ResponseEntity<PositionResponse> transact(OidcUser user, PositionRequest request, int factor) {
		return ResponseEntity.ok(new PositionEntityResponseSupplier(defaultPostfolioManager.create(new PositionRequestEntitySupplier(user, factor, request))).get());
	}

}
