package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntityType;

public interface BoardView {

	/**
	 * Returns the type of the {@link Entity} at this position.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the type of the {@link Entity} at this position
	 * @throws IndexOutOfBoundsException
	 *             if any of the given arguments are out of bounds
	 */
	EntityType getEntityType(int x, int y);

	/**
	 * Returns the size of this {@code BoardView} as {@link XY}.
	 * 
	 * @return the size of this BoardView as XY
	 */
	XY getSize();
}
