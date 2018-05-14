package de.hsa.games.fatsquirrel.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hsa.games.fatsquirrel.core.XY;

public abstract class MasterSquirrel extends PlayerEntity {

	protected final List<MiniSquirrel> children = Collections.unmodifiableList(new ArrayList<>());
	
	private static final int DEFAULT_ENERGY = 1000;

	public MasterSquirrel(int id, XY location) {
		super(id, DEFAULT_ENERGY, location);
	}
	
	public MiniSquirrel createChild(final XY direction, final int energy) {
		if (energy < 100) {
			throw new IllegalArgumentException("energy must be >= 100");
		}
		if (getEnergy() <= energy) {
			throw new IllegalArgumentException("getEnergy() must be > energy");
		}
		updateEnergy(-energy);
		final MiniSquirrel mini = new MiniSquirrel(0, energy, getLocation().add(direction));
		mini.master = this;
		return mini;
	}
	
	public boolean isChild(final MiniSquirrel squirrel) {
		return children.contains(squirrel);
	}
}
