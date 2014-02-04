package com.poker.sitngo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Objects;

public class Pot {

	private int chips;
	private int currentBet;
	
	private Set<Player> players;
	
	public Pot(int amount, int currentBet, List<Player> players) {
		chips = amount;
		this.currentBet = currentBet;
		this.players = new HashSet<Player>(players);
	}
	
	public void addBet(int amount, Player player) {
		if(!players.contains(player))
			players.add(player);
		chips += amount;
	}
	
	public void newBettingRound() {
		currentBet = 0;
	}
	
	public int getAmount() {
		return chips;
	}
	
	public void addPlayer(Player player) {
		if(!players.contains(player))
			players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(
				chips, currentBet, players);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Pot)) return false;
		Pot other = (Pot)obj;
		return Objects.equal(this.chips, other.chips)
				&& Objects.equal(this.currentBet, other.currentBet)
				&& Objects.equal(this.players, other.players);
	}
}
