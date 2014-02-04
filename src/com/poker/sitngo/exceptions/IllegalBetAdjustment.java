package com.poker.sitngo.exceptions;

public class IllegalBetAdjustment extends RuntimeException {
	
	public IllegalBetAdjustment() {
		super();
	}
	
	public IllegalBetAdjustment(String message) {
		super(message);
	}
}
