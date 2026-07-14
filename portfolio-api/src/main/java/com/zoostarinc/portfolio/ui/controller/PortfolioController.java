package com.zoostarinc.portfolio.ui.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.portfolio.model.PositionDetail;
import com.zoostarinc.portfolio.model.PositionSummary;
import com.zoostarinc.portfolio.service.PortfolioManager;
import com.zoostarinc.portfolio.ui.request.BuyPositionRequest;
import com.zoostarinc.portfolio.ui.request.BuyPositionRequestSupplier;
import com.zoostarinc.portfolio.ui.request.SellPositionRequest;
import com.zoostarinc.portfolio.ui.request.SellPositionRequestSupplier;
import com.zoostarinc.portfolio.ui.response.BuyPositionResponseSupplier;
import com.zoostarinc.portfolio.ui.response.PortfolioDetailResponseSupplier;
import com.zoostarinc.portfolio.ui.response.PortfolioSummaryResponseSupplier;
import com.zoostarinc.portfolio.ui.response.PositionEntityResponse;

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
	public ResponseEntity<Map<String, PositionSummary>> summary(@AuthenticationPrincipal DefaultOidcUser user) {
		return ResponseEntity.ok(new PortfolioSummaryResponseSupplier(defaultPostfolioManager.retrievePositionSummaryByTickerForUser(user.getSubject())).get());
	}

	@GetMapping(path = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, List<PositionDetail>>> detail(@AuthenticationPrincipal DefaultOidcUser user) {
		return ResponseEntity.ok(new PortfolioDetailResponseSupplier(defaultPostfolioManager.retrievePositionSummaryByTickerForUser(user.getSubject())).get());
	}

}
