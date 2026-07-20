package com.zoostarinc.portfolio.ui.controller;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SwaggerController implements ApplicationContextAware, InitializingBean {

	public static final String HOME_PAGE = "redirect:swagger-ui/index.html";

	@Value("${build.name}")
	protected String buildName;

	@Value("${build.number}")
	protected String buildNumber;

	@Value("${build.timestamp}")
	protected String buildTimestamp;

	@Value("${build.version}")
	protected String buildVersion;

	protected ApplicationContext applicationContext;

	/**
	 * 
	 * @param name  the message to be displayed
	 * @param model
	 * @return greeting message
	 */
	@GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String home(@AuthenticationPrincipal OidcUser user, HttpSession session) {
		log.info("Hello: {}!", user.toString());
		log.info("Your session ID: {}", session.getId());
		log.debug("Loading {} in env: {}", buildName,
				Arrays.toString(applicationContext.getEnvironment().getActiveProfiles()));
		return HOME_PAGE;
	}

	protected void determineLogLevelGranularity() {
		log.error("{}", "Logging at ERROR level");
		log.warn("{}", "Logging at WARN level");
		log.info("{}", "Logging at INFO level");
		log.debug("{}", "Logging at DEBUG level");
		log.trace("{}", "Logging at TRACE level");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		determineLogLevelGranularity();
	}

}
