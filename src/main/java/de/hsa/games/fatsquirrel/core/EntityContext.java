package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.entity.BadBeast;
import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.entity.GoodBeast;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;
import de.hsa.games.fatsquirrel.entity.PlayerEntity;

public interface EntityContext {

	/**
	 * Returns the size of the associated {@link Entity}.
	 * 
	 * @return the size of the associated Entity
	 */
	XY getSize();

	void tryMove(MiniSquirrel squirrel, XY moveDirection);

	void tryMove(GoodBeast beast, XY moveDirection);

	void tryMove(BadBeast beast, XY moveDirection);

	void tryMove(MasterSquirrel master, XY moveDirection);

	PlayerEntity nearestPlayerEntity(XY pos);

	void kill(Entity entity);

	void killAndReplace(Entity entity);

	EntityType getEntityType(XY pos);

	void spawnMiniSquirrel(final MiniSquirrel mini);
}
