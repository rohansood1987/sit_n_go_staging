package com.poker.sitngo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class StateTest {

	/*
	 * Test the equals method of the state object
	 * 1. Setup a state class 1
	 * 2. Setup state class 2 which has different players objects with same values
	 * 3. Validate if the two states are equivalent
	 */
	@Test
	public void testStateEquality() {

		Player alice = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH}); //big blind
		Player bob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		Player carol = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD}); //small blind
		Player[] players = new Player[]{alice ,bob, carol, null, null, null, null, null, null};

		State state = new State(new Card[5], players, new HashSet<Player>(Arrays.asList(carol, alice)), 
				bob, alice, bob, new ArrayList<Pot>(Arrays.asList(new Pot(30, 20, Arrays.asList(players)))));

		Player expectedAlice = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH});
		Player expectedBob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC});
		Player expectedCarol = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD});
		Player[] expectedPlayers = new Player[]{expectedAlice ,expectedBob, expectedCarol, null, null, null, null, null, null};

		State expectedState = new State(new Card[5], expectedPlayers, new HashSet<Player>(Arrays.asList(carol, alice)), 
				expectedBob, expectedAlice, expectedBob, new ArrayList<Pot>(Arrays.asList(new Pot(30, 20, Arrays.asList(expectedPlayers)))));

		assertEquals(state, expectedState);
	}


	@Test
	public void testValidCallMove() {
		int currentBet , bigBlind = 20, smallBlind = 10;	//big blind
		currentBet = bigBlind;
		
		Player alice = new Player(1980, bigBlind, 0, new Card[]{Card.AS, Card.AH}); //big blind
		Player bob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		Player carol = new Player(2390, smallBlind, 2, new Card[]{Card.QH, Card.QD}); //small blind
		Player[] players = new Player[]{alice ,bob, carol, null, null, null, null, null, null};

		State state = new State(new Card[5], players, new HashSet<Player>(Arrays.asList(carol, alice)), 
				bob, alice, bob, new ArrayList<Pot>(Arrays.asList(new Pot(bigBlind+smallBlind, currentBet, Arrays.asList(carol, alice)))));

		StateChanger stateChanger = new StateChanger(state);
		stateChanger.call(bob); // Bob calls

		Player expectedAlice = new Player(1980, currentBet, 0, new Card[]{Card.AS, Card.AH});
		Player expectedBob = new Player(1500-currentBet, currentBet, 1, new Card[]{Card.KD, Card.KC});
		Player expectedCarol = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD});
		Player[] expectedPlayers = new Player[]{expectedAlice ,expectedBob, expectedCarol, null, null, null, null, null, null};

		State expectedState = new State(new Card[5], expectedPlayers, new HashSet<Player>(Arrays.asList(carol, alice, bob)), 
				expectedBob, expectedAlice, expectedCarol, new ArrayList<Pot>(Arrays.asList(new Pot(bigBlind+bigBlind+smallBlind, currentBet, Arrays.asList(expectedCarol, expectedAlice, expectedBob)))));

		assertEquals(state, expectedState);
		
	}

	@Test
	public void testValidRaiseMove() {
		int currentBet , bigBlind = 20, smallBlind = 10;	//big blind
		currentBet = bigBlind;
		int raiseToAmount = 50;
		
		Player alice = new Player(1980, bigBlind, 0, new Card[]{Card.AS, Card.AH}); //big blind
		Player bob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		Player carol = new Player(2390, smallBlind, 2, new Card[]{Card.QH, Card.QD}); //small blind
		Player[] players = new Player[]{alice ,bob, carol, null, null, null, null, null, null};

		State state = new State(new Card[5], players, new HashSet<Player>(Arrays.asList(carol, alice)), 
				bob, alice, bob, new ArrayList<Pot>(Arrays.asList(new Pot(bigBlind + smallBlind, currentBet, Arrays.asList(carol, alice)))));

		StateChanger stateChanger = new StateChanger(state);
		stateChanger.raise(bob,raiseToAmount); // Bob calls
		currentBet = raiseToAmount;

		Player expectedAlice = new Player(1980, bigBlind, 0, new Card[]{Card.AS, Card.AH});
		Player expectedBob = new Player(1500-currentBet, currentBet, 1, new Card[]{Card.KD, Card.KC});
		Player expectedCarol = new Player(2390, smallBlind, 2, new Card[]{Card.QH, Card.QD});
		Player[] expectedPlayers = new Player[]{expectedAlice ,expectedBob, expectedCarol, null, null, null, null, null, null};
		int expectedPotSum = currentBet + bigBlind + smallBlind;
		
		State expectedState = new State(new Card[5], expectedPlayers, new HashSet<Player>(Arrays.asList(carol, alice, bob)), 
				expectedBob, expectedAlice, expectedCarol, 
				new ArrayList<Pot>(Arrays.asList(new Pot(expectedPotSum, currentBet, Arrays.asList(expectedCarol, expectedAlice, expectedBob)))));

		assertEquals(state, expectedState);
		
	}

	@Test
	public void testValidCheckMove() {
		int currentBet = 0, potValue = 500;
		Player currentBetter = null;
		
		Player alice = new Player(1980, currentBet, 0, new Card[]{Card.AS, Card.AH}); // (to act)
		Player bob = new Player(1500, currentBet, 1, new Card[]{Card.KD, Card.KC}); //dealer
		Player carol = new Player(2390, currentBet, 2, new Card[]{Card.QH, Card.QD}); 
		Player[] players = new Player[]{alice ,bob, carol, null, null, null, null, null, null};
		
		State state = new State(new Card[5], players, new HashSet<Player>(Arrays.asList(carol, alice,bob)), 
				bob, alice, currentBetter, new ArrayList<Pot>(Arrays.asList(new Pot(potValue, currentBet, Arrays.asList(carol, alice, bob)))));
		
		StateChanger stateChanger = new StateChanger(state);
		stateChanger.check(carol); // Carol checks
		
		Player expectedAlice = new Player(1980, currentBet, 0, new Card[]{Card.AS, Card.AH});
		Player expectedBob = new Player(1500, currentBet, 1, new Card[]{Card.KD, Card.KC}); //dealer
		Player expectedCarol = new Player(2390, currentBet, 2, new Card[]{Card.QH, Card.QD});
		Player[] expectedPlayers = new Player[]{expectedAlice ,expectedBob, expectedCarol, null, null, null, null, null, null};
		
		State expectedState = new State(new Card[5], expectedPlayers, new HashSet<Player>(Arrays.asList(carol, alice, bob)), 
				expectedBob, expectedCarol, currentBetter, 
				new ArrayList<Pot>(Arrays.asList(new Pot(potValue, currentBet, Arrays.asList(expectedCarol, expectedAlice, expectedBob)))));
			}
	
	@Test
	public void testValidFoldMove() {
		int currentBet = 20 ;
		int prevRoundPotAmount = 300, prevRoundbet = 100;
		
		
		Player alice = new Player(1980, currentBet, 0, new Card[]{Card.AS, Card.AH}); //called the bet by carol
		Player bob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		Player carol = new Player(2390, currentBet, 2, new Card[]{Card.QH, Card.QD}); //bet
		Player[] players = new Player[]{alice ,bob, carol, null, null, null, null, null, null};

		Pot prevRoundPot = new Pot(prevRoundPotAmount,prevRoundbet,Arrays.asList(carol,alice,bob) );
		int newPotAmount = currentBet*2;
		Pot newPot = new Pot(newPotAmount, currentBet, Arrays.asList(carol, alice));
		
		State state = new State(new Card[5], players, new HashSet<Player>(Arrays.asList(carol, alice, bob)), 
				bob, alice, bob, new ArrayList<Pot>(Arrays.asList(newPot,prevRoundPot)));

		StateChanger stateChanger = new StateChanger(state);
		stateChanger.fold(bob); // Bob folds
		
		//round finishes currentbet sets to 0 for players involved
		currentBet = 0;
		Player expectedAlice = new Player(1980, currentBet, 0, new Card[]{Card.AS, Card.AH});
		Player expectedBob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC});
		Player expectedCarol = new Player(2390, currentBet, 2, new Card[]{Card.QH, Card.QD});
		Player[] expectedPlayers = new Player[]{expectedAlice ,expectedBob, expectedCarol, null, null, null, null, null, null};
		
		//add up the amount of pots of two rounds and remove bob from pot share list
		Pot expectedPot = new Pot(prevRoundPotAmount + newPotAmount, currentBet, Arrays.asList(expectedCarol,expectedAlice));
		State expectedState = new State(new Card[5], expectedPlayers, new HashSet<Player>(Arrays.asList(carol, alice, bob)), 
				expectedBob, expectedAlice, expectedCarol, new ArrayList<Pot>(Arrays.asList(expectedPot)));

		assertEquals(state, expectedState);
			}
}