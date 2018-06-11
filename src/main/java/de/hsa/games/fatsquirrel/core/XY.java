package de.hsa.games.fatsquirrel.core;

public final class XY {
	public final int x;
	public final int y;

	public static final XY ZERO_ZERO = new XY(0, 0);
	public static final XY RIGHT = new XY(1, 0);
	public static final XY LEFT = new XY(-1, 0);
	public static final XY UP = new XY(0, -1);
	public static final XY DOWN = new XY(0, 1);
	public static final XY RIGHT_UP = new XY(1, -1);
	public static final XY RIGHT_DOWN = new XY(1, 1);
	public static final XY LEFT_UP = new XY(-1, -1);
	public static final XY LEFT_DOWN = new XY(-1, 1);

	public XY() {
		x = y = 0;
	}
	
	public XY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public XY plus(XY xy) {
		return new XY(x + xy.x, y + xy.y);
	}

	public XY minus(XY location) {
		return plus(location.times(-1));
	}

	public XY times(int factor) {
		return new XY(x * factor, y * factor);
	}

	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	public double distanceFrom(XY xy) {
		final int distanceX = x - xy.x;
		final int distanceY = y - xy.y;
		return Math.max(Math.abs(distanceX), Math.abs(distanceY));
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
}
