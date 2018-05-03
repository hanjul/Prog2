package de.hsa.games.fatsquirrel.entity;

import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.util.Assert;

public abstract class PlayerEntity extends Character {

	private int stunnedRounds;
	
	public PlayerEntity(int id, int energy, XY location) {
		super(id, energy, location);
	}
	
	public boolean isStunned() {
		return stunnedRounds > 0;
	}
	
	public void stun(final int stunnedRounds) {
		Assert.isTrue(stunnedRounds >= 0, "stunnedRounds must be >= 0");
		this.stunnedRounds = stunnedRounds;
	}
}
