package de.hsa.games.fatsquirrel.entity;

import de.hsa.games.fatsquirrel.core.XY;

public abstract class PlayerEntity extends Character {

	private static final int DEFAULT_STUNNED_ROUNDS = 3;
	
	public PlayerEntity(int id, int energy, XY location) {
		super(id, energy, location);
	}
	
	public void stun() {
		this.roundsTillNextMove = DEFAULT_STUNNED_ROUNDS;
	}
}
