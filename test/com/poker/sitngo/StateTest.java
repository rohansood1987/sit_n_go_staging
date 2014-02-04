package com.poker.sitngo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class StateTest {
	
	Player[] players = null;
	Player alice, bob, carol;
	State state;
	Pot pot;
	@Before
	public void Setup() {
		int initialAmount = 30;
		int currentBet = 20;
		// new Player(int chips, int currentBet, int position, Card[] cards)
		alice = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH}); //big blind
		bob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC}); //dealer (to act)
		carol = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD}); //small blind
		players = new Player[]{alice ,bob, carol, null, null, null, null, null, null};
		pot = new Pot(initialAmount, currentBet, Arrays.asList(players));
		state = new State(new Card[5], players, new HashSet<Player>(Arrays.asList(carol, alice)), 
				bob, alice, bob, new ArrayList<Pot>(Arrays.asList(pot)));
	}
	
	
	/*
	 * This test tests the equals method of the state object
	 * 
	 * 1. Initialize a set of players with all the attributes. Junit Setup() method does this
	 * 2. Setup a state class 1 which uses players initialized in step1
	 * 3. Initialize a different set of players with same value of attributes as in 1
	 * 4. Setup state class2 which has players initialized in step 3.
	 * 5. The two state objects are refering to different player and other objects with equal values, 
	 *    Hence the equals method should consider them equal.
	 */
	@Test
	public void testStateEquality() {
		
		
		//state 1 initialized in the Setup() method	
		
		//initialize players for state2
		Player expectedAlice = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH});
		Player expectedBob = new Player(1500, 0, 1, new Card[]{Card.KD, Card.KC});
		Player expectedCarol = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD});
		Player[] expectedPlayers = new Player[]{expectedAlice ,expectedBob, expectedCarol, null, null, null, null, null, null};
		
		//initialize state 2
		State expectedState = new State(new Card[5], expectedPlayers, new HashSet<Player>(Arrays.asList(carol, alice)), 
				expectedBob, expectedAlice, expectedBob, new ArrayList<Pot>(Arrays.asList(new Pot(30, 20, Arrays.asList(expectedPlayers)))));
		//Assert if both states are equal
		assertEquals(state, expectedState);
	}
	
	/*
	 * This test tests the Call by a player
	 * 1. Execute the call move by a player on state 1
	 * 2. Setup state2 ,the expected state, with increased pot value and decreased chips of the player
	 * 3. Assert if state 1's values are equal to state 2's values after executing the call move
	 */
	@Test
	public void testValidCallMove() {
		
		
		//initialize a statechanger which manipulates the 'state' variable
		StateChanger stateChanger = new StateChanger(state);
		// Bob calls
		stateChanger.call(bob);
		
		/*
		 * setup the expected State
		 */
		Player expectedAlice = new Player(1980, 20, 0, new Card[]{Card.AS, Card.AH});
		//reduce expected bob's chips by the currentBet (equal to 20 for state, initialized in setup method) 
		Player expectedBob = new Player(1500-20, 20, 1, new Card[]{Card.KD, Card.KC});
		Player expectedCarol = new Player(2390, 10, 2, new Card[]{Card.QH, Card.QD});
		Player[] expectedPlayers = new Player[]{expectedAlice ,expectedBob, expectedCarol, null, null, null, null, null, null};
		// increase the value of the expected pot by the amount called by bob
		Pot expectedPot = new Pot(pot.getAmount()+20, 20, Arrays.asList(expectedCarol, expectedAlice, expectedBob));
		
		State expectedState = new State(new Card[5], expectedPlayers, new HashSet<Player>(Arrays.asList(carol, alice, bob)), 
				expectedBob, expectedAlice, expectedCarol, new ArrayList<Pot>(Arrays.asList(expectedPot)));
		
		//assert that initial state is equal to the expected state.
		assertEquals(state, expectedState);
		
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
