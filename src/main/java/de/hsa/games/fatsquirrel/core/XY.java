package de.hsa.games.fatsquirrel.core;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class XY {

	public static final XY UP = new XY(1, 0);
	public static final XY UP_LEFT = new XY(1, -1);
	public static final XY UP_RIGHT = new XY(1, 1);
	public static final XY DOWN = new XY(-1, 0);
	public static final XY DOWN_LEFT = new XY(-1, -1);
	public static final XY DOWN_RIGHT = new XY(-1, 1);
	public static final XY RIGHT = new XY(0, 1);
	public static final XY LEFT = new XY(0, -1);
	
	private static final Set<XY> DIRECTIONS = new HashSet<>();
	
	static {
		DIRECTIONS.add(UP);
		DIRECTIONS.add(UP_LEFT);
		DIRECTIONS.add(UP_RIGHT);
		DIRECTIONS.add(DOWN);
		DIRECTIONS.add(DOWN_LEFT);
		DIRECTIONS.add(DOWN_RIGHT);
		DIRECTIONS.add(RIGHT);
		DIRECTIONS.add(LEFT);
	}
	
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
		Objects.requireNonNull(other, "other must not be null");
		return new XY(x + other.x, y + other.y);
	}
	
	public XY add(final Direction direction) {
		Objects.requireNonNull(direction, "direction must not be null");
		return new XY(x + direction.getDeltaX(), y + direction.getDeltaY());
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
}
