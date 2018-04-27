package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.XY;

public final class Wall extends Entity {

	private static final int DEFAULT_ENERGY = -10;

	public Wall(final int id, final XY location) {
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
		final Wall other = (Wall) obj;
		return getId() == other.getId() && getEnergy() == other.getEnergy()
				&& Objects.equals(getLocation(), other.getLocation());
	}
}
