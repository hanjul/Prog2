package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.command.Command;
import de.hsa.games.fatsquirrel.console.GameCommandType;
import de.hsa.games.fatsquirrel.util.Assert;

public abstract class Game implements Runnable {

	private final State state;
	private final UI ui;
	
	private Command currentCommand = new Command(GameCommandType.NONE);
	
	private static final int FPS = 5;
	private static final int OPTIMAL_TIME = 1_000_000_000 / FPS;

	public void setCurrentCommand(final Command currentCommand) {
		this.currentCommand = Assert.notNull(currentCommand, "currentCommand must not be null");
	}
	
	public Command getCurrentCommand() {
		return currentCommand;
	}
	
	/**
	 * Constructs a new {@code Game} using the given {@link State}.
	 * 
	 * @param state
	 *            the state of the game
	 * @throws if
	 *             {@code state == null}
	 */
	public Game(final State state, final UI ui) {
		this.state = Assert.notNull(state, "state must not be null");
		this.ui = Assert.notNull(ui, "ui must not be null");
	}

	@Override
	public void run() {
		long lastLoopTime = System.nanoTime();
		long lastFpsTime = 0;
		int fps = 0;
		while (true) {
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
	}

	protected abstract void processInput();

	protected abstract void render();

	public void update() {
		state.update();
	}

	public State getState() {
		return state;
	}
	
	public UI getUI() {
		return ui;
	}
}
