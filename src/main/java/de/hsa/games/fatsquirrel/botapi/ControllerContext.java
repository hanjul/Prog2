package de.hsa.games.fatsquirrel.botapi;

import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.EntityType;

public interface ControllerContext {

	XY getViewLowerLeft();
	XY getViewUpperRight();
	EntityType getEntityAt(XY xy);
	void move(XY direction);
	void spawnMiniBot(XY direction, int energy);
	int getEnergy();
}
