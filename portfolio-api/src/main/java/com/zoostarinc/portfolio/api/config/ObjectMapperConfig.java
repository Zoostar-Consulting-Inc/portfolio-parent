package com.zoostarinc.portfolio.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ObjectMapperConfig {

	@Bean
	ObjectMapper objectMapper() {
		// Create and configure the mapper
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// 1. Register the Java 8 Date/Time module
		objectMapper.registerModule(new JavaTimeModule());
	
		// 2. (Optional but Recommended) Prevent Jackson from writing Instants/Dates as timestamps (like [2026,8,29])
		// This forces it to use standard ISO-8601 strings (e.g., "2026-08-29T16:22:00Z")
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		return objectMapper;
	}

}
