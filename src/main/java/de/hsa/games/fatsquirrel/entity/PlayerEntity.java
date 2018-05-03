package de.hsa.games.fatsquirrel.entity;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.util.Assert;

public abstract class PlayerEntity extends Character {

	private static final int DEFAULT_STUNNED_ROUNDS = 3;
	
	private int stunnedRounds;
	
	public PlayerEntity(int id, int energy, XY location) {
		super(id, energy, location);
	}
	
	@Override
	public void nextStep(EntityContext context) {
		Assert.notNull(context, "context must not be null");
		if (stunnedRounds >= 0) {
			stunnedRounds--;
		}
	}
	
	public boolean isStunned() {
		return stunnedRounds >= 0;
	}
	
	public void stun() {
		stun(DEFAULT_STUNNED_ROUNDS);
	}
	
	public void stun(final int stunnedRounds) {
		Assert.isTrue(stunnedRounds >= 0, "stunnedRounds must be >= 0");
		this.stunnedRounds = stunnedRounds;
	}
}
