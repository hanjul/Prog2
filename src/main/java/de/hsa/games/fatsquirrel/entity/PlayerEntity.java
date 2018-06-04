package de.hsa.games.fatsquirrel.entity;

import de.hsa.games.fatsquirrel.core.XY;

public abstract class PlayerEntity extends Character {

	private static final int DEFAULT_STUNNED_ROUNDS = 4;
	
	private XY direction = XY.ZERO_ZERO;
	
	public PlayerEntity(int energy, XY location) {
		super(energy, location, 0);
	}
	
	public void setDirection(final XY direction) {
		this.direction = direction;
	}
	
	public XY getDirection() {
		return direction;
	}
	
	public void stun() {
		this.roundsTillNextMove = DEFAULT_STUNNED_ROUNDS;
	}
}
