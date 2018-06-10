package de.hsa.games.fatsquirrel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.FlattenedBoard;
import de.hsa.games.fatsquirrel.entity.Entity;

public class State {

	private static final Logger logger = LoggerFactory.getLogger(State.class);
	
	private final Board board;
	private Map<String, List<Integer>> highscores;
	private FlattenedBoard flat;
	private final Map<Entity, String> bots;
	
	public State(final Board board, final Map<Entity, String> bots) {
		this.board = Objects.requireNonNull(board);
		flat = flattenedBoard();
		this.bots = bots;
		highscores = new HashMap<>();
		for (String bot : bots.values()) {
			highscores.put(bot, new ArrayList<>());
		}
	}
	
	public void update() {
		board.getEntities().nextStep(flat);
	}
	
	public Map<String, List<Integer>> getHighscores() {
		return highscores;
	}
	
	public void endRound() {
		logger.debug("ending round");
		for (Map.Entry<Entity, String> bot : bots.entrySet()) {
			highscores.get(bot.getValue()).add(bot.getKey().getEnergy());
		}
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
