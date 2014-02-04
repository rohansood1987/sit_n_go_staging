package com.poker.sitngo;

import java.util.Arrays;

import com.google.common.base.Objects;
import com.poker.sitngo.exceptions.IllegalBetAdjustment;

public class Player {

	private int chips;
	private int position;
	
	private int currentBet;
	
	private Card[] cards;
	
	public Player(int startingChips, int position) {
		this.chips = startingChips;
		this.position = position;
		cards = new Card[2];
	}
	
	public Player(int chips, int currentBet, int position, Card[] cards) {
		if(cards == null)
			throw new IllegalArgumentException();
		if(cards.length != 2)
			throw new IllegalArgumentException();
		
		this.chips = chips;
		this.currentBet = currentBet;
		this.position = position;
		this.cards = cards;
	}
	
	public void dealCard(Card card) {
		if(cards == null)
			throw new NullPointerException();
		
		if(cards[0] ==  null) cards[0] = card;
		else if(cards[1] == null) cards[1] = card;
		else throw new IllegalStateException("Hole cards already dealt.");
	}
	
	public int getChips() {
		return chips;
	}
	
	public int getPosition() {
		return position;
	}
	
	public int getCurrentBet() {
		return currentBet;
	}
	
	public void adjustBetBy(int amount) {
		if(amount > chips)
			throw new IllegalBetAdjustment("Not Enough Chips");
		if(amount < 0 && amount*(-1) > currentBet)
			throw new IllegalBetAdjustment("Returned Amount Exceeds Current Bet");
		
		chips -= amount;
		currentBet += amount;
	}
	
	public void resetBet(int amount) {
		chips += amount;
		currentBet = 0;
	}
	
	public Card[] getCards() {
		return cards;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(
				chips, position, currentBet, Arrays.hashCode(cards));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Player)) return false;
		Player other = (Player)obj;
		return Objects.equal(this.chips, other.chips)
				&& Objects.equal(this.position, other.position)
				&& Objects.equal(this.currentBet, other.currentBet)
				&& Arrays.equals(this.cards, other.cards);
	}
}
