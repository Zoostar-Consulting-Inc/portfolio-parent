package com.zoostarinc.portfolio.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.portfolio.api.response.PositionDetailResponse;
import com.zoostarinc.portfolio.api.response.PositionResponse;
import com.zoostarinc.portfolio.api.response.PositionSummaryResponse;
import com.zoostarinc.portfolio.api.util.function.PortfolioDetailResponseSupplier;
import com.zoostarinc.portfolio.api.util.function.PositionEntityResponseSupplier;
import com.zoostarinc.portfolio.api.util.function.PositionSummaryResponseSupplier;
import com.zoostarinc.portfolio.api.util.function.Utils;
import com.zoostarinc.portfolio.service.PortfolioManager;
import com.zoostarinc.portfolio.util.function.UpdatePositionDetailRequestSupplier;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PortfolioCrudController {
	
	private final PortfolioManager defaultPostfolioManager;

	@GetMapping(path = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionSummaryResponse> summary(@RequestParam(required = false) String ticker) {
		return ResponseEntity.ok(new PositionSummaryResponseSupplier(defaultPostfolioManager.retrievePositionSummaryByTickerForUser(Utils.getCurrentSubject(), ticker)).get());
	}

	@GetMapping(path = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, List<PositionDetailResponse>>> detail(@RequestParam(required = false) String ticker) {
		return ResponseEntity.ok(new PortfolioDetailResponseSupplier(defaultPostfolioManager.retrievePositionSummaryByTickerForUser(Utils.getCurrentSubject(), ticker)).get());
	}

	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionResponse> update(@RequestBody PositionResponse request) {
		return ResponseEntity.ok(new PositionEntityResponseSupplier(defaultPostfolioManager.update(new UpdatePositionDetailRequestSupplier(Utils.getCurrentSubject(), request))).get());
	}

	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, List<PositionDetailResponse>>> delete(@RequestParam String positionId) {
		return ResponseEntity.ok(new PortfolioDetailResponseSupplier(defaultPostfolioManager.delete(Utils.getCurrentSubject(), positionId)).get());
	}

}
