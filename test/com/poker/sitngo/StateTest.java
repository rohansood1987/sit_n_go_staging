package com.poker.sitngo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class StateTest {
	
	@Test
	public void testStateEquality() {
		
		Player alice = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH}); //big blind
		Player bob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		Player carol = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD}); //small blind
		Player[] players = new Player[]{alice ,bob, carol, null, null, null, null, null, null};
		
		State state = new State(new Card[5], players, new HashSet<Player>(Arrays.asList(carol, alice)), 
				bob, alice, bob, new ArrayList<Pot>(Arrays.asList(new Pot(30, 20, Arrays.asList(players)))));
		
		//do something
		
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
		
		Player alice = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH}); //big blind
		Player bob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		Player carol = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD}); //small blind
		Player[] players = new Player[]{alice ,bob, carol, null, null, null, null, null, null};
		
		State state = new State(new Card[5], players, new HashSet<Player>(Arrays.asList(carol, alice)), 
				bob, alice, bob, new ArrayList<Pot>(Arrays.asList(new Pot(30, 20, Arrays.asList(carol, alice)))));
		
		StateChanger stateChanger = new StateChanger(state);
		stateChanger.call(bob); // Bob calls
		
		Player expectedAlice = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH});
		Player expectedBob = new Player(1480, 20, 1, new Card[]{Card.KD, Card.KC});
		Player expectedCarol = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD});
		Player[] expectedPlayers = new Player[]{expectedAlice ,expectedBob, expectedCarol, null, null, null, null, null, null};
		
		State expectedState = new State(new Card[5], expectedPlayers, new HashSet<Player>(Arrays.asList(carol, alice, bob)), 
				expectedBob, expectedAlice, expectedCarol, new ArrayList<Pot>(Arrays.asList(new Pot(50, 20, Arrays.asList(expectedCarol, expectedAlice, expectedBob)))));
		
		assertEquals(state, expectedState);
		
		/*Player player0 = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH}); //big blind
		Player player1 = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		Player player2 = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD}); //small blind
		
		State state = new State();
		state.addPlayer(player0);
		state.addPlayer(player1);
		state.addPlayer(player2);
		
		state.setCurrentBet(20);
		state.setDealer(player1);
		state.setCurrentBetter(player0);// big blind is the current better
		state.setCurrentPlayer(player1);// player 1 to act
		state.setCurrentPot(new Pot(30, Arrays.asList(player0, player1, player2)));
		
		StateChanger stateChanger = new StateChanger(state);
		stateChanger.call(player1); //player 1 calls
		
		Player expectedPlayer0 = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH});
		Player expectedPlayer1 = new Player(1480, 20, 1, new Card[]{Card.KD, Card.KC});
		Player expectedPlayer2 = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD});
		
		State expectedState = new State();
		expectedState.addPlayer(expectedPlayer0);
		expectedState.addPlayer(expectedPlayer1);
		expectedState.addPlayer(expectedPlayer2);
		
		expectedState.setCurrentBet(20);
		expectedState.setDealer(expectedPlayer1);
		expectedState.setCurrentBetter(expectedPlayer0);
		expectedState.setCurrentPlayer(expectedPlayer2);
		expectedState.setCurrentPot(new Pot(50, Arrays.asList(player0, player1, player2)));
		
		assertEquals(expectedState, state);*/
	}
	
	@Test
	public void testValidRaiseMove() {
		
		/*Player player0 = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH}); //big blind
		Player player1 = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		Player player2 = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD}); //small blind
		
		State state = new State();
		state.addPlayer(player0);
		state.addPlayer(player1);
		state.addPlayer(player2);
		
		state.setCurrentBet(20);
		state.setDealer(player1);
		state.setCurrentBetter(player0);// big blind is the current better
		state.setCurrentPlayer(player1);// player 1 to act
		state.setCurrentPot(new Pot(30, Arrays.asList(player0, player1, player2)));
		
		StateChanger stateChanger = new StateChanger(state);
		stateChanger.raise(player1, 60); //player 1 raises to 60 
		
		Player expectedPlayer0 = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH});
		Player expectedPlayer1 = new Player(1440, 60, 1, new Card[]{Card.KD, Card.KC});
		Player expectedPlayer2 = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD});
		
		State expectedState = new State();
		expectedState.addPlayer(expectedPlayer0);
		expectedState.addPlayer(expectedPlayer1);
		expectedState.addPlayer(expectedPlayer2);
		
		expectedState.setCurrentBet(60);
		expectedState.setDealer(expectedPlayer1);
		expectedState.setCurrentBetter(expectedPlayer1);
		expectedState.setCurrentPlayer(expectedPlayer2);
		expectedState.setCurrentPot(new Pot(90, Arrays.asList(player0, player1, player2)));
		
		assertEquals(expectedState, state);*/
	}
	
	@Test
	public void testValidFoldMove() {
		
		/*Player player0 = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH}); //big blind
		Player player1 = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		Player player2 = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD}); //small blind
		
		State state = new State();
		state.addPlayer(player0);
		state.addPlayer(player1);
		state.addPlayer(player2);
		
		state.setCurrentBet(20);
		state.setDealer(player1);
		state.setCurrentBetter(player0);// big blind is the current better
		state.setCurrentPlayer(player1);// player 1 to act
		state.setCurrentPot(new Pot(30, Arrays.asList(player0, player1, player2)));
		
		StateChanger stateChanger = new StateChanger(state);
		stateChanger.fold(player1); //player 1 folds
		
		Player expectedPlayer0 = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH});
		Player expectedPlayer1 = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC});
		Player expectedPlayer2 = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD});
		
		State expectedState = new State();
		expectedState.addPlayer(expectedPlayer0);
		expectedState.addPlayer(expectedPlayer1);
		expectedState.addPlayer(expectedPlayer2);
		
		expectedState.setCurrentBet(20);
		expectedState.setDealer(expectedPlayer1);
		expectedState.setCurrentBetter(expectedPlayer0);
		expectedState.setCurrentPlayer(expectedPlayer2);
		expectedState.setCurrentPot(new Pot(30, Arrays.asList(player0, player2)));
		
		assertEquals(expectedState, state);*/
	}
}
