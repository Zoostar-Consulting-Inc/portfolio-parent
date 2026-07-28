package com.zoostarinc.portfolio.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Controller
public class IndexController {
	
	@Value("${api.base.url}")
	private String apiBaseUrl;

	@GetMapping(path = "/")
	public String index(@AuthenticationPrincipal OidcUser user, HttpSession session) {
		log.info("Hello User: {}", user);
		session.setAttribute("name", user.getFullName());
		session.setAttribute("apiBaseUrl", apiBaseUrl);
		return "index";
	}
}
