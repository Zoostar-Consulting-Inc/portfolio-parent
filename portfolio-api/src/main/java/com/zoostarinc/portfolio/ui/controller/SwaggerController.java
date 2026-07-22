package com.zoostarinc.portfolio.ui.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SwaggerController {

	public static final String HOME_PAGE = "redirect:swagger-ui/index.html";

	/**
	 * 
	 * @param name  the message to be displayed
	 * @param model
	 * @return greeting message
	 */
	@GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String home(@AuthenticationPrincipal OidcUser user) {
		log.info("Loading Swagger UI for user: {}!", user.toString());
		return HOME_PAGE;
	}

}
