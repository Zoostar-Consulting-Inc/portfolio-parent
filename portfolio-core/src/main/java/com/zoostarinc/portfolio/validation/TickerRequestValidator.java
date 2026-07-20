package com.zoostarinc.portfolio.validation;

import java.util.function.UnaryOperator;

import org.springframework.util.StringUtils;

public class TickerRequestValidator implements UnaryOperator<String> {
	
	public static final TickerRequestValidator INSTANCE = new TickerRequestValidator();
	
	private TickerRequestValidator() {
		super();
	}
	
	@Override
	public String apply(String ticker) {
		if(StringUtils.hasText(ticker)) {
			return ticker.trim().toUpperCase();
		}
		throw new IllegalArgumentException("Ticker is required");
	}

}
