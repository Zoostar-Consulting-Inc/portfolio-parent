package com.zoostarinc.portfolio.web.controller;

import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.zoostarinc.portfolio.service.PortfolioApiService;

import jakarta.servlet.http.HttpSession;
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
	public String index(@AuthenticationPrincipal OidcUser user, @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client, Model model, HttpSession session, ClientHttpRequest request) {
		log.info("Hello User: {}", user);
		var token = client.getAccessToken().getTokenValue();
		if(!StringUtils.hasText(token)) {
			log.warn("Access token is empty for user: {}", user.getFullName());
		}
		model.addAttribute("name", user.getFullName());
		model.addAttribute("accessToken", token);
		
		var value = portfolioApiManager.getPortfolioSummary(session.getId(), token, null);
		log.info("Response: {}", value);
		
		return "portfolio";
	}
	
}
