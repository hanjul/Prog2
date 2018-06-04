package de.hsa.games.fatsquirrel.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.util.Assert;

public abstract class Character extends Entity {

	private static final Logger logger = LoggerFactory.getLogger(Character.class.getName());

	private final int roundTimeout;
	protected int roundsTillNextMove;

	protected Character(int energy, XY location, int roundTimeout) {
		super(energy, location);
		logger.trace("A new Character ({}) was created", getClass().getName());
		this.roundTimeout = roundTimeout;
	}

	public final void nextStep(EntityContext context) {
		Assert.notNull(context, "context must not be null");
		if (roundsTillNextMove > 0) {
			logger.trace("{} can't move this round", this);
			roundsTillNextMove--;
			return;
		}
		logger.trace("{} will attempt to move this round", this);
		onNextStep(context);
		roundsTillNextMove = roundTimeout;
	}

	public abstract void onNextStep(EntityContext context);

	public boolean canMove() {
		return roundsTillNextMove <= 0;
	}
}
