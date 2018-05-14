package de.hsa.games.fatsquirrel.console;

import java.util.List;

import de.hsa.games.fatsquirrel.Game;
import de.hsa.games.fatsquirrel.State;
import de.hsa.games.fatsquirrel.command.Command;
import de.hsa.games.fatsquirrel.command.ScanException;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.HandOperatedMasterSquirrel;

public class ConsoleGame extends Game {

	private final List<HandOperatedMasterSquirrel> masters;

	public ConsoleGame(State state) {
		super(state, new ConsoleUI());
		this.masters = state.getBoard().getAll(HandOperatedMasterSquirrel.class);
	}

	@Override
	protected void processInput() {
		for (final HandOperatedMasterSquirrel m : masters) {
			if (m.canMove()) {
				boolean askAgain = true;
				while (askAgain) {
					try {
						final Command command = getUI().getCommand();
						switch (command.getCommandType().getName()) {
						case "up":
							m.setDirection(XY.LEFT);
							askAgain = false;
							break;
						case "down":
							m.setDirection(XY.RIGHT);
							askAgain = false;
							break;
						case "left":
							m.setDirection(XY.DOWN);
							askAgain = false;
							break;
						case "right":
							m.setDirection(XY.UP);
							askAgain = false;
							break;
						case "master_energy":
							System.out.println("Master Energy: " + m.getEnergy());
							askAgain = true;
							break;
						case "exit":
							System.exit(0);
							askAgain = true;
							break;
						default:
							getUI().message(command.getCommandType().getHelpText());
							askAgain = true;
							break;
						}
					} catch (ScanException e) {
						e.printStackTrace();
						askAgain = true;
					}
				}
			}
		}
	}

	@Override
	protected void render() {
		getUI().render(getState().flattenedBoard());
	}
}
