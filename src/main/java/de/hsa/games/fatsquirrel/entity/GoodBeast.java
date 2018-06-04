package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.core.XYSupport;

public class GoodBeast extends Character {

	private static final int DEFAULT_ENERGY = 200;
	private static final int ROUND_TIMEOUT = 3;

	public GoodBeast(XY location) {
		super(DEFAULT_ENERGY, location, ROUND_TIMEOUT);
	}

	@Override
	public void onNextStep(EntityContext context) {
		if (!canMove()) {
			roundsTillNextMove--;
			return;
		}
		roundsTillNextMove = ROUND_TIMEOUT;
		final Entity player = context.nearestPlayerEntity(getLocation());
		if (player != null) {
			if (player.getLocation().distanceFrom(getLocation()) <= 6) {
				final XY direction = XYSupport.clamp(player.getLocation().minus(getLocation()));
				context.tryMove(this, direction.times(-1));
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
