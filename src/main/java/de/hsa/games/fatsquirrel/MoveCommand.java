package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.util.Assert;

public enum MoveCommand {

	MOVE_UP (XY.UP),
	MOVE_LEFT(XY.LEFT),
	MOVE_RIGHT(XY.RIGHT),
	MOVE_DOWN(XY.DOWN);
	
	private final XY direction;
	
	private MoveCommand(final XY direction) {
		this.direction = Assert.notNull(direction, "direction must not be null");
	}
	
	public XY getDirection() {
		return direction;
	}
}
