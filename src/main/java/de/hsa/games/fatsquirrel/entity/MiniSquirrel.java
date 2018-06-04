package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.SpawnException;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.core.XYSupport;

public class MiniSquirrel extends PlayerEntity {

	MasterSquirrel master;
	
	public MiniSquirrel(int energy, XY location) {
		super(energy, location);
		if (energy < 100) {
			throw new SpawnException("energy must be >= 100");
		}
	}

	@Override
	public void onNextStep(EntityContext context) {
		updateEnergy(-1);
		context.tryMove(this, XYSupport.randomDirection());
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
	
	public MasterSquirrel getMaster() {
		return master;
	}
}
