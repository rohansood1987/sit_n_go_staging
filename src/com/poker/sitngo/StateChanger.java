package com.poker.sitngo;


public class StateChanger {
	
	private State state;
	
	public StateChanger(State state) {
		this.state = state;
	}
	
	public void check(Player player) {
		//TODO: Check
	}
	
	public void bet(Player player, int betAmount) {
		//TODO: Bet
	}
	
	public void call(Player player) {
		//TODO: Call
	}
	
	public void raise(Player player, int raiseToAmount) {
		//TODO: Raise
	}
	
	public void allIn(Player player) {
		//TODO: All-in
	}
	
	public void fold(Player player) {
		//TODO: Fold
	}
	
}
