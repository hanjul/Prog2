package de.hsa.games.fatsquirrel;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.FlattenedBoard;

public class State {

	private final Board board;
	private int highscore;
	
	public State(final Board board) {
		this.board = Objects.requireNonNull(board);
	}
	
	public void update() {
		
	}
	
	public int getHighscore() {
		return highscore;
	}
	
	public FlattenedBoard flattenedBoard() {
		return board.flatten();
	}
}
