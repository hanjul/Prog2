package de.hsa.games.fatsquirrel.core;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import de.hsa.games.fatsquirrel.util.Assert;

public final class XY {

	public static final XY UP = new XY(1, 0);
	public static final XY UP_LEFT = new XY(1, -1);
	public static final XY UP_RIGHT = new XY(1, 1);
	public static final XY DOWN = new XY(-1, 0);
	public static final XY DOWN_LEFT = new XY(-1, -1);
	public static final XY DOWN_RIGHT = new XY(-1, 1);
	public static final XY RIGHT = new XY(0, 1);
	public static final XY LEFT = new XY(0, -1);

	private static final List<XY> DIRECTIONS = Arrays.asList(UP, UP_LEFT, UP_RIGHT, DOWN, DOWN_LEFT, DOWN_RIGHT, RIGHT,
			LEFT);

	private final int x;
	private final int y;

	public XY(final int xy) {
		this.x = this.y = xy;
	}

	public XY(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public XY reverse() {
		return new XY(-x, -y);
	}

	public XY add(final XY other) {
		Assert.notNull(other, "other must not be null");
		return new XY(x + other.x, y + other.y);
	}

	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	public int gridLength() {
		return Math.max(Math.abs(x), Math.abs(y));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XY other = (XY) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XY [x=" + x + ", y=" + y + "]";
	}

	public XY minus(XY location) {
		return add(location.reverse());
	}
	
	public static XY randomDirection() {
		return DIRECTIONS.get(ThreadLocalRandom.current().nextInt(DIRECTIONS.size()));
	}
}
