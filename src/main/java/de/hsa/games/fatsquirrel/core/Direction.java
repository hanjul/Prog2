package de.hsa.games.fatsquirrel.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
	
	UP(1, 0), UP_LEFT(1, -1), UP_RIGHT(1, 1), DOWN(-1, 0), DOWN_LEFT(-1, -1), DOWN_RIGHT(-1, 1), RIGHT(0, 1), LEFT(0,
			-1);
	
	private static final Map<Integer, Direction> KEY_MAP = new HashMap<>();
	
	static {
		KEY_MAP.put(1, UP);
		KEY_MAP.put(2, DOWN);
		KEY_MAP.put(3, RIGHT);
		KEY_MAP.put(4, LEFT);
	}

	private final int deltaX;
	private final int deltaY;

	private Direction(final int deltaX, final int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public Direction reverse() {
		switch (this) {
		case UP:
			return DOWN;
		case UP_LEFT:
			return DOWN_RIGHT;
		case UP_RIGHT:
			return DOWN_LEFT;
		case DOWN:
			return UP;
		case DOWN_LEFT:
			return UP_RIGHT;
		case DOWN_RIGHT:
			return UP_LEFT;
		case RIGHT:
			return LEFT;
		case LEFT:
			return RIGHT;
		default:
			throw new InternalError("this == null");
		}
	}

	public XY asXY() {
		return new XY(deltaX, deltaY);
	}
	
	/**
	 * Returns a random {@code Direction}.
	 * 
	 * @return a random Direction
	 */
	public static Direction random() {
		return Direction.values()[ThreadLocalRandom.current().nextInt(0, 8)];
	}
	
	public static Direction fromKey(final int key) {
		return KEY_MAP.get(key);
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++)
		System.out.println(Direction.random());
	}
}
