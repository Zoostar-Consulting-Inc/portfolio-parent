package com.zoostarinc.portfolio.ui.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.portfolio.model.PositionSummary;
import com.zoostarinc.portfolio.service.PortfolioManager;
import com.zoostarinc.portfolio.ui.request.BuyPositionRequest;
import com.zoostarinc.portfolio.ui.request.SellPositionRequest;
import com.zoostarinc.portfolio.ui.response.PositionEntityResponse;
import com.zoostarinc.portfolio.util.function.BuyPositionRequestSupplier;
import com.zoostarinc.portfolio.util.function.BuyPositionResponseSupplier;
import com.zoostarinc.portfolio.util.function.PortfolioSummaryResponseSupplier;
import com.zoostarinc.portfolio.util.function.SellPositionRequestSupplier;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PortfolioController {
	
	private final PortfolioManager defaultPostfolioManager;

	@PostMapping(path = "/buy", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionEntityResponse> buy(@AuthenticationPrincipal DefaultOidcUser user, @RequestBody BuyPositionRequest request) {
		return ResponseEntity.ok(new BuyPositionResponseSupplier(defaultPostfolioManager.create(new BuyPositionRequestSupplier(user, request))).get());
	}

	@PostMapping(path = "/sell", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionEntityResponse> sell(@AuthenticationPrincipal DefaultOidcUser user, @RequestBody SellPositionRequest request) {
		return ResponseEntity.ok(new BuyPositionResponseSupplier(defaultPostfolioManager.create(new SellPositionRequestSupplier(user, request))).get());
	}

	@GetMapping(path = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, PositionSummary>> retrieve(@AuthenticationPrincipal DefaultOidcUser user) {
		return ResponseEntity.ok(new PortfolioSummaryResponseSupplier(defaultPostfolioManager.retrievePositionsByUser(user.getSubject())).get());
	}

}
