package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.XY;

public class GoodPlant extends Entity {

	private static final int DEFAULT_ENERGY = 100;
	
	public GoodPlant(int id, XY location) {
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
		final GoodPlant other = (GoodPlant) obj;
		return getId() == other.getId() && getEnergy() == other.getEnergy() && Objects.equals(getLocation(), other.getLocation());
	}
}
