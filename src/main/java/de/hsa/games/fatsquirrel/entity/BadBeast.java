package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;

public class BadBeast extends Character {

	private static final int DEFAULT_ENERGY = -150;

	public BadBeast(int id, XY location) {
		super(id, DEFAULT_ENERGY, location);
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
		final BadBeast other = (BadBeast) obj;
		return getId() == other.getId() && getEnergy() == other.getEnergy() && Objects.equals(getLocation(), other.getLocation());
	}
}
