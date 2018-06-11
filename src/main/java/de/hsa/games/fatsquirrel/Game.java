package de.hsa.games.fatsquirrel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.hsa.games.fatsquirrel.command.Command;
import de.hsa.games.fatsquirrel.console.GameCommandType;
import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.util.Assert;

public abstract class Game implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Game.class);

	private State state;
	private final UI ui;

	private int steps;
	private int currentSteps;

	private final Map<String, List<Integer>> highscores;
	private final Map<Entity, String> bots;

	private Command currentCommand = new Command(GameCommandType.NONE);

	private volatile boolean running = true;

	private Supplier<State> nextState;

	private static final int FPS = 5;
	private static final int OPTIMAL_TIME = 1_000_000_000 / FPS;

	public void setCurrentCommand(final Command currentCommand) {
		this.currentCommand = Assert.notNull(currentCommand, "currentCommand must not be null");
	}

	public Command getCurrentCommand() {
		return currentCommand;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, List<Integer>> loadHighscores(final Collection<String> botNames) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		File user = new File(System.getProperty("user.dir")  + "/highscores.json");
		Map<String, List<Integer>> result;
		try {
			result = mapper.readValue(user, Map.class);
		} catch (IOException e) {
			logger.warn("unable to load highscores", e);
			result = new HashMap<>();
		}
		for (String s : botNames) {
			result.putIfAbsent(s, new ArrayList<>());
		}
		return result;
	}

	/**
	 * Constructs a new {@code Game} using the given {@link State}.
	 * 
	 * @param state the state of the game
	 * @throws if {@code state == null}
	 */
	public Game(final State state, final UI ui, final Map<Entity, String> bots) {
		this.state = Assert.notNull(state, "state must not be null");
		this.ui = Assert.notNull(ui, "ui must not be null");
		this.bots = Assert.notNull(bots, "bots must not be null");
		highscores = loadHighscores(bots.values());
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	@Override
	public void run() {
		long lastLoopTime = System.nanoTime();
		long lastFpsTime = 0;
		int fps = 0;
		currentSteps = 0;
		while (running) {
			final long now = System.nanoTime();
			final long updateLength = now - lastLoopTime;
			lastLoopTime = now;
//			double delta = updateLength / ((double) OPTIMAL_TIME);

			lastFpsTime += updateLength;
			fps++;

			if (lastFpsTime >= 1_000_000_000) {
				getUI().message("FPS: " + fps);
				lastFpsTime = 0;
				fps = 0;
			}

			render();
			processInput();
			update();

			try {
				Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		saveHighscores();
	}
	
	private void saveHighscores() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		File user = new File(System.getProperty("user.dir") + "/highscores.json");
		try {
			mapper.writeValue(user, highscores);
		} catch (IOException e) {
			logger.warn("unable to save highscores", e);
		}
	}

	protected abstract void processInput();

	protected abstract void render();

	public void update() {
		if (currentSteps++ < steps) {
			state.update();
		} else {
			for (Map.Entry<Entity, String> e : bots.entrySet()) {
				highscores.get(e.getValue()).add(e.getKey().getEnergy());
				logger.info("Highscores of bot {}: {}", e.getValue(), highscores.get(e.getValue()));
			}
			if (nextState != null) {
				final State s = nextState.get();
				if (s != null) {
					state = s;
				} else {
					running = false;
				}
			} else {
				running = false;
			}
		}
	}

	public Map<String, List<Integer>> getHighscores() {
		return highscores;
	}

	public State getState() {
		return state;
	}

	public UI getUI() {
		return ui;
	}
}
