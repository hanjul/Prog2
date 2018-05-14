package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;

public class BadBeast extends Character {

	private static final int DEFAULT_ENERGY = -150;
	private static final int ROUND_TIMEOUT = 4;
	private int livesLeft;
	
	public BadBeast(int id, XY location) {
		super(id, DEFAULT_ENERGY, location);
	}

	@Override
	public void nextStep(EntityContext context) {
		System.out.println(1);
		if (!canMove()) {
			System.out.println(2);
			roundsTillNextMove--;
			return;
		}
		System.out.println(3);
		System.out.println("Rounds: " + roundsTillNextMove);
		roundsTillNextMove = ROUND_TIMEOUT;
		final Entity player = context.nearestPlayerEntity(getLocation());
		if (player != null) {
			final XY length = player.getLocation().minus(getLocation());
			if (length.gridLength() <= 6) {
				final XY direction = length.clamp();
				context.tryMove(this, direction);
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		final BadBeast other = (BadBeast) obj;
		return getId() == other.getId() && getEnergy() == other.getEnergy() && Objects.equals(getLocation(), other.getLocation());
	}

	public void removeLive() {
		livesLeft--;
	}
	
	public int getLivesLeft() {
		return livesLeft;
	}


	@Override
	public EntityType getType() {
		return EntityType.BAD_BEAST;
	}
}
