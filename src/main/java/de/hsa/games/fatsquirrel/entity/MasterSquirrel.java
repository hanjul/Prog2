package de.hsa.games.fatsquirrel.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hsa.games.fatsquirrel.core.XY;

public abstract class MasterSquirrel extends PlayerEntity {

	protected final List<MiniSquirrel> children = new ArrayList<>();
	
	private static final int DEFAULT_ENERGY = 1000;

	public MasterSquirrel(XY location) {
		super(DEFAULT_ENERGY, location);
	}
	
	public MiniSquirrel createChild(final XY direction, final int energy) {
		if (energy < 100) {
			throw new IllegalArgumentException("energy must be >= 100");
		}
		if (getEnergy() <= energy) {
			throw new IllegalArgumentException("getEnergy() must be > energy");
		}
		updateEnergy(-energy);
		final MiniSquirrel mini = new MiniSquirrel(energy, getLocation().plus(direction));
		children.add(mini);
		mini.master = this;
		return mini;
	}
	
	public boolean isChild(final MiniSquirrel squirrel) {
		return children.contains(squirrel);
	}
	
	public List<MiniSquirrel> getChildren() {
		return Collections.unmodifiableList(children);
	}
	
	@Override
	public EntityType getType() {
		return EntityType.MASTER_SQUIRREL;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}
}
