package de.hsa.games.fatsquirrel.entity;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;

public abstract class Character extends Entity {

	protected Character(int id, int energy, XY location) {
		super(id, energy, location);
	}

	public abstract void nextStep(EntityContext context);
}
