package de.hsa.games.fatsquirrel.entity;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.util.Assert;

public abstract class Character extends Entity {

	private static final Logger logger = Logger.getLogger(Character.class.getName());

	protected int roundsTillNextMove;

	protected Character(int energy, XY location) {
		super(energy, location);
		if (logger.getLevel() == Level.FINEST) {
			logger.log(Level.FINEST, "A new Character (" + getClass().getName() + ") was created");
		}
	}

	public void nextStep(EntityContext context) {
		Assert.notNull(context, "context must not be null");
		if (roundsTillNextMove >= 0) {
			logger.log(Level.FINE, "{0} can't move this round");
			roundsTillNextMove--;
		} else {
			logger.log(Level.FINE, "{0} will attempt to move this round");
		}
	}

	public boolean canMove() {
		return roundsTillNextMove <= 0;
	}
}
