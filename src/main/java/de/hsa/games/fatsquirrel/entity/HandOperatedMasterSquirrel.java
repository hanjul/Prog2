package de.hsa.games.fatsquirrel.entity;

import java.util.Objects;

import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.util.Assert;

public class HandOperatedMasterSquirrel extends MasterSquirrel {

	private final UI ui;

	public HandOperatedMasterSquirrel(final int id, final XY location, final UI ui) {
		super(id, location);
		this.ui = Assert.notNull(ui, "ui must not be null");
	}

	@Override
	public void nextStep(EntityContext context) {
		context.tryMove(this, ui.getCommand().getDirection());
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
		final HandOperatedMasterSquirrel other = (HandOperatedMasterSquirrel) obj;
		return getId() == other.getId() && getEnergy() == other.getEnergy() && Objects.equals(getLocation(), other.getLocation());
	}
}
