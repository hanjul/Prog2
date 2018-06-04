package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;

public class HandOperatedMasterSquirrel extends MasterSquirrel {

	public HandOperatedMasterSquirrel(final XY location) {
		super(location);
	}

	@Override
	public void onNextStep(EntityContext context) {
		context.tryMove(this, getDirection());
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
		final HandOperatedMasterSquirrel other = (HandOperatedMasterSquirrel) obj;
		return getId() == other.getId() && getEnergy() == other.getEnergy()
				&& Objects.equals(getLocation(), other.getLocation());
	}

	@Override
	public EntityType getType() {
		return EntityType.MASTER_SQUIRREL;
	}
}
