package com.zoostarinc.portfolio.api.util.function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Utils {

	private Utils() {
		// Private constructor to prevent instantiation
	}
	
	public static String getCurrentSubject() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof Jwt jwt) {
        	log.debug("Found JWT principal: {}", jwt);
            return jwt.getSubject();
        } 
        
        if (principal instanceof OidcUser oidcUser) {
        	log.debug("Found OIDC principal: {}", oidcUser);
            return oidcUser.getSubject();
        }

        return null;
    }
	
}
