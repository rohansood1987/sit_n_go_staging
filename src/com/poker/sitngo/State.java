package com.poker.sitngo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Objects;

/**
 * 
 * State class for a No-limit Texas Hold'em Poker cash or ring game.
 * <p>
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Texas_hold_%27em">Texas Hold'em
 *      Poker</a>
 * @see <a href="http://en.wikipedia.org/wiki/Cash_game">Cash game</a>
 * 
 * @author Rohan Sood and Shwetank Raghuvanshi
 * 
 */
public class State {

	/** Maximum number of players on the poker table */
	public static final int MAX_PLAYER_NUM = 9;
	/** Value of the Small blind */
	public static final int SMALL_BLIND = 10;
	/** Value of the Big blind */
	public static final int BIG_BLIND = 20;

	/**
	 * Contains the 5 community cards to be placed faced up on the poker table.
	 * <p>
	 * <ul>
	 * <li>Flop - First three cards
	 * <li>Turn - Fourth card
	 * <li>River - Fifth card
	 * </ul>
	 */
	private Card[] board;

	/**
	 * {@link Player} array containing the players currently sitting at the
	 * table.
	 * <p>
	 * A <code>null</code> value represents an empty seat.
	 */
	private Player[] players;

	/** Set of players part of the current hand */
	private Set<Player> playersInCurrentHand;

	/**
	 * Reference to the {@link Player} who is the current dealer
	 */
	private Player dealer;

	/**
	 * Reference to the {@link Player} who is either the last player to raise,
	 * or to bet, or to start the current betting round.
	 * <p>
	 * 
	 * The current betting session will end one seat before the current better,
	 * except when he/she is also the big blind and current bet value equals big
	 * blind, in which case he/she will have the option to fold/check/raise.
	 */
	private Player currentBetter;

	/**
	 * Reference to the {@link Player} who has to act next.
	 */
	private Player playerToAct;

	/**
	 * List consisting of the main pot and all the additional side pots in the
	 * current hand. Side pots are created when a player goes all-in.
	 * <p>
	 * 
	 * The List should contain at least one Pot element during any hand, and the
	 * last Pot element will always be the current main/side pot.
	 */
	private List<Pot> pots;

	public State() {
		board = new Card[5];
		players = new Player[9];
		playersInCurrentHand = new HashSet<Player>();
		pots = new ArrayList<Pot>();
	}

	/**
	 * Provides a pre-initialized State object for testing purposes.
	 * 
	 * @param board
	 * @param players
	 * @param playersInCurrentHand
	 * @param dealer
	 * @param currentBetter
	 * @param playerToAct
	 * @param pots
	 */
	public State(Card[] board, Player[] players,
			Set<Player> playersInCurrentHand, Player dealer,
			Player currentBetter, Player playerToAct, List<Pot> pots) {
		this.board = board;
		this.players = players;
		this.playersInCurrentHand = playersInCurrentHand;
		this.dealer = dealer;
		this.currentBetter = currentBetter;
		this.playerToAct = playerToAct;
		this.pots = pots;
	}

	public void setFlop(Card[] cards) {
		if (cards == null || cards.length != 3) {
			throw new IllegalArgumentException("Invalid Flop "
					+ Arrays.asList(cards));// TODO: check
		}

		for (int i = 0; i < 3; i++) {
			board[i] = cards[i];
		}
	}

	public void setTurn(Card card) {
		board[3] = card;
	}

	public void setRiver(Card card) {
		board[4] = card;
	}

	public void setBoard(Card[] board) {
		this.board = board;
	}

	public Card[] getBoard() {
		return board;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		int playerPosition = player.getPosition();
		if (playerPosition < 0 || playerPosition >= MAX_PLAYER_NUM) {
			throw new IllegalArgumentException("Invalid seat postition "
					+ playerPosition);
		}
		if (players[playerPosition] != null) {
			throw new IllegalArgumentException("Seat already occupied");
		}

		players[playerPosition] = player;
	}

	public Player removePlayer(int seatPosition) {
		Player player = players[seatPosition];
		players[seatPosition] = null;
		return player;
	}

	public void setPlayersInCurrentHand(Set<Player> playersInCurrentHand) {
		this.playersInCurrentHand = playersInCurrentHand;
	}

	public Set<Player> getPlayersInCurrentHand() {
		return playersInCurrentHand;
	}

	public void setDealer(Player dealer) {
		this.dealer = dealer;
	}

	public Player getDealer() {
		return dealer;
	}

	public void setCurrentBetter(Player currentBetter) {
		this.currentBetter = currentBetter;
	}

	public Player getCurrentBetter() {
		return currentBetter;
	}

	public void setPlayerToAct(Player playerToAct) {
		this.playerToAct = playerToAct;
	}

	public Player getPlayerToAct() {
		return playerToAct;
	}

	public void setPots(List<Pot> pots) {
		this.pots = pots;
	}

	public List<Pot> getPots() {
		return pots;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(Arrays.hashCode(board),
				Arrays.deepHashCode(players), playersInCurrentHand, dealer,
				currentBetter, playerToAct, pots);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof State)) {
			return false;
		}
		State other = (State) obj;
		return Arrays.equals(board, other.board)
				&& Arrays.deepEquals(players, other.players)
				&& Objects.equal(playersInCurrentHand,
						other.playersInCurrentHand)
				&& Objects.equal(dealer, other.dealer)
				&& Objects.equal(currentBetter, other.currentBetter)
				&& Objects.equal(playerToAct, other.playerToAct)
				&& Objects.equal(pots, other.pots);
	}
}
