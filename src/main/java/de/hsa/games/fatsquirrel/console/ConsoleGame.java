package de.hsa.games.fatsquirrel.console;

import java.util.ArrayList;
import java.util.List;

import de.hsa.games.fatsquirrel.Game;
import de.hsa.games.fatsquirrel.State;
import de.hsa.games.fatsquirrel.command.Command;
import de.hsa.games.fatsquirrel.entity.HandOperatedMasterSquirrel;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;

public class ConsoleGame extends Game {
	
	public static final List<HandOperatedMasterSquirrel> MASTERS = new ArrayList<>();
	
	public ConsoleGame(State state) {
		super(state, new ConsoleUI());
	}

	@Override
	protected void processInput() {
		for (HandOperatedMasterSquirrel master : MASTERS) {
			if (master.canMove()) {
				final Command command = getUI().getCommand();
				
			}
		}
	}

	@Override
	protected void render() {
		getUI().render(getState().flattenedBoard());
	}
}
