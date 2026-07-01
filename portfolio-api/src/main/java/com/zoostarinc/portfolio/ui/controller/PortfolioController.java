package com.zoostarinc.portfolio.ui.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.portfolio.model.PositionSummary;
import com.zoostarinc.portfolio.service.PortfolioManager;
import com.zoostarinc.portfolio.util.function.PortfolioSummaryResponseSupplier;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PortfolioController {
	
	private final PortfolioManager defaultPostfolioManager;

	@GetMapping(path = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, PositionSummary>> retrieve(@AuthenticationPrincipal DefaultOidcUser user) {
		return ResponseEntity.ok(new PortfolioSummaryResponseSupplier(defaultPostfolioManager.retrievePositionsByUser(user.getSubject())).get());
	}
	
}
