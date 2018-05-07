package de.hsa.games.fatsquirrel;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.FlattenedBoard;

public class State {

	private final Board board;
	private int highscore;
	private FlattenedBoard flat;
	
	public State(final Board board) {
		this.board = Objects.requireNonNull(board);
		flat = flattenedBoard();
	}
	
	public void update() {
		board.getEntities().nextStep(flat);
	}
	
	public int getHighscore() {
		return highscore;
	}
	
	public FlattenedBoard flattenedBoard() {
		return board.flatten();
	}
	
	@Override
	public String toString() {
		return "Highscore: " + highscore + "\n" + board.toString();
	}
}
