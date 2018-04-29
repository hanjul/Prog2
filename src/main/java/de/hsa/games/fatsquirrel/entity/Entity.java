package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.XY;

public abstract class Entity {

	private final int id;
	private int energy;
	private XY location;
	
	protected Entity(final int id, final int energy, final XY location) {
		this.id = id;
		this.energy = energy;
		setLocation(location);
	}
	
	public int getId() {
		return id;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public void updateEnergy(final int energyDelta) {
		this.energy += energyDelta;
	}
	
	public void setLocation(final XY location) {
		this.location = Objects.requireNonNull(location, "location must not be null");
	}
	
	public XY getLocation() {
		return location;
	}
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", energy=" + energy + ", location=" + location + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + energy;
		result = prime * result + id;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}
	
	public abstract EntityType getType();
}
