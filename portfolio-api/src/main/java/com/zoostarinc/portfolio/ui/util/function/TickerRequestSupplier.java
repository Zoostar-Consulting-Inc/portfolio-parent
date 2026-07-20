package com.zoostarinc.portfolio.ui.util.function;

import java.util.function.Supplier;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class TickerRequestSupplier implements Supplier<String> {
	
	private final String ticker;
	
	@Override
	public String get() {
		if(StringUtils.hasText(ticker)) {
			return ticker.trim().toUpperCase();
		}
		throw new IllegalArgumentException("Ticker is required");
	}

}
