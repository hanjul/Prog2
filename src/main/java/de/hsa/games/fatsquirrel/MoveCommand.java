package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.util.Assert;

public enum MoveCommand {

	MOVE_UP (new XY(0, 1)),
	MOVE_LEFT(new XY(-1, 0)),
	MOVE_RIGHT(new XY(1, 0)),
	MOVE_DOWN(new XY(0, -1));
	
	private final XY direction;
	
	private MoveCommand(final XY direction) {
		this.direction = Assert.notNull(direction, "direction must not be null");
	}
	
	public XY getDirection() {
		return direction;
	}
}
