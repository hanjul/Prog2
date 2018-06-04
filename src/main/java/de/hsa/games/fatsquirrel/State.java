package de.hsa.games.fatsquirrel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.FlattenedBoard;
import de.hsa.games.fatsquirrel.entity.EntityType;

public class State {

	private static final Logger logger = LoggerFactory.getLogger(State.class);
	
	private final Board board;
	private Map<String, Integer> highscores;
	private FlattenedBoard flat;
	
	public State(final Board board) {
		this.board = Objects.requireNonNull(board);
		flat = flattenedBoard();
		highscores = new HashMap<>();
	}
	
	public void update() {
		board.getEntities().nextStep(flat);
	}
	
	public Map<String, Integer> getHighscores() {
		return highscores;
	}
	
	public void endRound() {
//		for () {
//			
//		}
	}
	
	public Board getBoard() {
		return board;
	}
	
	public FlattenedBoard flattenedBoard() {
		return board.flatten();
	}
	
	@Override
	public String toString() {
		return "Highscore: " + highscores + "\n" + board.toString();
	}
}
