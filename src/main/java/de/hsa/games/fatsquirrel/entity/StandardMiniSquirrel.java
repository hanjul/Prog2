package de.hsa.games.fatsquirrel.entity;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;

public class StandardMiniSquirrel extends MiniSquirrel {

	public StandardMiniSquirrel(int id, int energy, XY location) {
		super(id, energy, location);
	}

	@Override
	public void nextStep(EntityContext context) {
		super.nextStep(context);
		if (!canMove()) {
			return;
		}
		context.tryMove(this, XY.randomDirection());
	}
}
