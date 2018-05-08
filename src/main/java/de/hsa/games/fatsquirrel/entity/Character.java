package de.hsa.games.fatsquirrel.entity;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.util.Assert;

public abstract class Character extends Entity {

	protected int roundsTillNextMove;
	
	protected Character(int id, int energy, XY location) {
		super(id, energy, location);
	}

	public void nextStep(EntityContext context) {
		Assert.notNull(context, "context must not be null");
		if (roundsTillNextMove >= 0) {
			roundsTillNextMove--;
		}
	}
	
	public boolean canMove() {
		return roundsTillNextMove <= 0;
	}
}
