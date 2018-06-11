package de.hsa.games.fatsquirrel;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.FlattenedBoard;

public class State {

	private static final Logger logger = LoggerFactory.getLogger(State.class);
	
	private final Board board;
	private FlattenedBoard flat;
	
	public State(final Board board) {
		this.board = Objects.requireNonNull(board);
		flat = flattenedBoard();
	}
	
	public void update() {
		board.getEntities().nextStep(flat);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public FlattenedBoard flattenedBoard() {
		return board.flatten();
	}
	
	@Override
	public String toString() {
		return board.toString();
	}
}
