package com.poker.sitngo;

import java.util.ArrayList;
import java.util.List;

import com.poker.sitngo.Card.Rank;
import com.poker.sitngo.Card.Suit;

public class Deck {
	
	private List<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>();
		for(Suit suit : Suit.values()) {
			for(Rank rank : Rank.values()) {
				cards.add(Card.get(rank, suit));
			}
		}
	}
	
	public void shuffle() {
		//TODO: Shuffle cards
	}

}
