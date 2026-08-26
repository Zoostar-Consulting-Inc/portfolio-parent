package com.zoostarinc.portfolio.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zoostarinc.portfolio.service.PortfolioApiService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for serving portfolio dashboard views.
 */
@Slf4j
@Getter
@Controller
@RequiredArgsConstructor
public class PortfolioController {

	private final PortfolioApiService portfolioApiManager;

	/**
	 * Serves the portfolio summary dashboard.
	 * 
	 * @param model the Spring Model to pass data to the view
	 * @return the view name
	 */
	@GetMapping(path = "/")
	public String index(@AuthenticationPrincipal OidcUser user, Model model) {
		log.info("Hello User: {}", user);
		model.addAttribute("name", user.getFullName());
		
		var value = portfolioApiManager.getPortfolioSummary(null);
		log.info("Response: {}", value);
		
		model.addAttribute("message", "Hello " + user.getFullName() + ", welcome to your portfolio dashboard!");
		
		return "portfolio";
	}
	
}
