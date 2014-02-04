package com.poker.sitngo;

public class Card {
	
	public static enum Suit {
		SPADES, DIAMONDS, HEARTS, CLUBS;
	}
	
	public static enum Rank {
		TWO, THREE, FOUR, FIVE,
		SIX, SEVEN, EIGHT, NINE, TEN,
		JACK, QUEEN, KING, ACE;
	}
	
	private Suit suit;
	private Rank rank;
	
	private static Card[][] cards;
	
	private Card (Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	static {
		cards = new Card[13][4];
		for(Rank rank : Rank.values()) {
			for(Suit suit : Suit.values()) {
				cards[rank.ordinal()][suit.ordinal()] = new Card(rank, suit);
			}
		}
	}
	
	public static Card get(Rank rank, Suit suit) {
		return cards[rank.ordinal()][suit.ordinal()];
	}
	
	public static final Card AS = get(Rank.ACE, Suit.SPADES);
	public static final Card AH = get(Rank.ACE, Suit.HEARTS);
	public static final Card AD = get(Rank.ACE, Suit.DIAMONDS);
	public static final Card AC = get(Rank.ACE, Suit.CLUBS);
	
	public static final Card KS = get(Rank.KING, Suit.SPADES);
	public static final Card KH = get(Rank.KING, Suit.HEARTS);
	public static final Card KD = get(Rank.KING, Suit.DIAMONDS);
	public static final Card KC = get(Rank.KING, Suit.CLUBS);
	
	public static final Card QS = get(Rank.QUEEN, Suit.SPADES);
	public static final Card QH = get(Rank.QUEEN, Suit.HEARTS);
	public static final Card QD = get(Rank.QUEEN, Suit.DIAMONDS);
	public static final Card QC = get(Rank.QUEEN, Suit.CLUBS);
}
