package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;

public class MiniSquirrel extends PlayerEntity {

	public MiniSquirrel(int id, int energy, XY location) {
		super(id, energy, location);
	}

	@Override
	public void nextStep(EntityContext context) {
		// NO-IMPL
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
}
