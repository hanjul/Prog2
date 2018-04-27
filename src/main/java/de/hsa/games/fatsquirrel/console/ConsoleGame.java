package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.Game;
import de.hsa.games.fatsquirrel.State;

public class ConsoleGame extends Game {
	
	public ConsoleGame(State state) {
		super(state, new ConsoleUI());
	}

	@Override
	protected void processInput() {
		
	}

	@Override
	protected void render() {
		
	}
}
