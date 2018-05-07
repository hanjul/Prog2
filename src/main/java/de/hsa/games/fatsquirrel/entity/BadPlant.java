package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.XY;

public class BadPlant extends Entity {

	private static final int DEFAULT_ENERGY = -100;

	public BadPlant(int id, XY location) {
		super(id, DEFAULT_ENERGY, location);
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
		final BadPlant other = (BadPlant) obj;
		return getId() == other.getId() && getEnergy() == other.getEnergy() && Objects.equals(getLocation(), other.getLocation());
	}

	@Override
	public EntityType getType() {
		return EntityType.BAD_PLANT;
	}
}
