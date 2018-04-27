package de.hsa.games.fatsquirrel.entity;

import de.hsa.games.fatsquirrel.core.XY;

public abstract class MasterSquirrel extends PlayerEntity {

	private static final int DEFAULT_ENERGY = 1000;

	public MasterSquirrel(int id, XY location) {
		super(id, DEFAULT_ENERGY, location);
	}
}
