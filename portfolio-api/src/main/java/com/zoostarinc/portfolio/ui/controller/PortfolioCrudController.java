package com.zoostarinc.portfolio.ui.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.portfolio.model.PositionDetail;
import com.zoostarinc.portfolio.model.PositionSummary;
import com.zoostarinc.portfolio.service.PortfolioManager;
import com.zoostarinc.portfolio.ui.response.PositionEntityResponse;
import com.zoostarinc.portfolio.ui.util.function.PortfolioDetailResponseSupplier;
import com.zoostarinc.portfolio.ui.util.function.PortfolioSummaryResponseSupplier;
import com.zoostarinc.portfolio.ui.util.function.PositionEntityResponseSupplier;
import com.zoostarinc.portfolio.ui.util.function.UpdatePositionDetailRequestSupplier;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.StringWrapper;

@RestController
@RequiredArgsConstructor
public class PortfolioCrudController {
	
	private final PortfolioManager defaultPostfolioManager;

	@GetMapping(path = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, PositionSummary>> summary(@AuthenticationPrincipal OidcUser user, @RequestParam(required = false) String ticker) {
		return ResponseEntity.ok(new PortfolioSummaryResponseSupplier(defaultPostfolioManager.retrievePositionSummaryByTickerForUser(user.getSubject(), ticker)).get());
	}

	@GetMapping(path = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, List<PositionDetail>>> detail(@AuthenticationPrincipal OidcUser user, @RequestParam(required = false) String ticker) {
		return ResponseEntity.ok(new PortfolioDetailResponseSupplier(defaultPostfolioManager.retrievePositionSummaryByTickerForUser(user.getSubject(), ticker)).get());
	}

	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionEntityResponse> update(@AuthenticationPrincipal OidcUser user, @RequestBody PositionEntityResponse request) {
		return ResponseEntity.ok(new PositionEntityResponseSupplier(defaultPostfolioManager.update(new UpdatePositionDetailRequestSupplier(user, request))).get());
	}

	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, List<PositionDetail>>> delete(@AuthenticationPrincipal OidcUser user, @RequestBody StringWrapper positionId) {
		return ResponseEntity.ok(new PortfolioDetailResponseSupplier(defaultPostfolioManager.delete(user.getSubject(), positionId)).get());
	}

}
