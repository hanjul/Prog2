package de.hsa.games.fatsquirrel.entity;

import de.hsa.games.fatsquirrel.core.XY;

public abstract class PlayerEntity extends Character {

	public PlayerEntity(int id, int energy, XY location) {
		super(id, energy, location);
	}
}
