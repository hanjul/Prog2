package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.util.Assert;

public abstract class Game implements Runnable {

	private final State state;
	private final UI ui;

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
		while (true) {
			render();
			processInput();
			update();
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
