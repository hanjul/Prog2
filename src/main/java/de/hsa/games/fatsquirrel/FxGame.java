package de.hsa.games.fatsquirrel;

import java.util.List;

import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.HandOperatedMasterSquirrel;

public class FxGame extends Game {

	private final List<HandOperatedMasterSquirrel> masters;

	public FxGame(State state, UI ui) {
		super(state, ui);
		this.masters = state.getBoard().getAll(HandOperatedMasterSquirrel.class);
	}

	@Override
	protected void processInput() {
		setCurrentCommand(getUI().getCommand());
		if (!masters.isEmpty()) {
			final HandOperatedMasterSquirrel master = masters.get(0);
			switch (getCurrentCommand().getCommandType().getName()) {
			case "up":
				master.setDirection(XY.LEFT);
				break;
			case "down":
				master.setDirection(XY.RIGHT);
				break;
			case "left":
				master.setDirection(XY.DOWN);
				break;
			case "right":
				master.setDirection(XY.UP);
				break;
			}
		}
	}

	@Override
	protected void render() {
		getUI().render(getState().flattenedBoard());
	}
}
