package com.poker.sitngo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Objects;

/**
 * Represents a side-pot or the main-pot in a poker hand
 * 
 * @author Rohan Sood and Shwetank Raghuvanshi
 * 
 */
public class Pot {

	/**
	 * The total chips in this pot so far.
	 */
	private int chips;

	/**
	 * The current bet/raise value to be called in the current betting session.
	 * In case everyone has checked so far, value will be zero. The value will
	 * also be reset to zero after the end of every betting session.
	 */
	private int currentBet;

	/**
	 * Set of players who are part of this pot. It will always be a subset of
	 * the <code>State.playersInCurrentHand</code> field.
	 */
	private Set<Player> players;

	public Pot() {
		players = new HashSet<Player>();
	}

	/**
	 * Provides a pre-initialized Pot object for testing purposes.
	 * 
	 * @param amount
	 * @param currentBet
	 * @param players
	 */
	public Pot(int amount, int currentBet, List<Player> players) {
		chips = amount;
		this.currentBet = currentBet;
		this.players = new HashSet<Player>(players);
	}

	public void addBet(int amount, Player player) {
		if (!players.contains(player))
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
		if (!players.contains(player))
			players.add(player);
	}

	public void removePlayer(Player player) {
		players.remove(player);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(chips, currentBet, players);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pot))
			return false;
		Pot other = (Pot) obj;
		return Objects.equal(this.chips, other.chips)
				&& Objects.equal(this.currentBet, other.currentBet)
				&& Objects.equal(this.players, other.players);
	}
}
