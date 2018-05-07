package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;

public class MiniSquirrel extends PlayerEntity {

	MasterSquirrel master;
	
	public MiniSquirrel(int id, int energy, XY location) {
		super(id, energy, location);
	}

	@Override
	public void nextStep(EntityContext context) {
		super.nextStep(context);
		if (!canMove()) {
			return;
		}
		updateEnergy(-1);
		context.tryMove(this, XY.randomDirection());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		final MiniSquirrel other = (MiniSquirrel) obj;
		return getId() == other.getId() && getEnergy() == other.getEnergy() && Objects.equals(getLocation(), other.getLocation());
	}

	@Override
	public EntityType getType() {
		return EntityType.MINI_SQUIRREL;
	}
	
	public boolean isSibling(final MiniSquirrel squirrel) {
		return master.isChild(squirrel);
	}
}
