package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;

public class GoodBeast extends Character {

	private static final int DEFAULT_ENERGY = 200;
	private static final int ROUND_TIMEOUT = 4;

	public GoodBeast(int id, XY location) {
		super(id, DEFAULT_ENERGY, location);
	}

	@Override
	public void nextStep(EntityContext context) {
		if (!canMove()) {
			roundsTillNextMove--;
		}
		roundsTillNextMove = ROUND_TIMEOUT;
		final Entity player = context.nearestPlayerEntity(getLocation());
		if (player != null) {
			final XY length = player.getLocation().minus(getLocation());
			if (length.gridLength() <= 6) {
				final XY direction = length.clamp();
				context.tryMove(this, direction.reverse());
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
		final GoodBeast other = (GoodBeast) obj;
		return getId() == other.getId() && getEnergy() == other.getEnergy() && Objects.equals(getLocation(), other.getLocation());
	}

	@Override
	public EntityType getType() {
		return EntityType.GOOD_BEAST;
	}
}
