package de.hsa.games.fatsquirrel.core;

import static de.hsa.games.fatsquirrel.core.XY.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class XYSupport {

	public static final List<XY> DIRECTIONS = Arrays.asList(UP, LEFT_UP, RIGHT_UP, DOWN, LEFT_DOWN, RIGHT_DOWN, RIGHT,
			LEFT);
	
	public static XY randomDirection() {
		return DIRECTIONS.get(ThreadLocalRandom.current().nextInt(DIRECTIONS.size()));
	}
	
	public static XY clamp(final XY xy) {
		return clamp(xy, -1, 1);
	}
	
	public static XY clamp(final XY xy, final int min, final int max) {
		if (min > max) {
			throw new IllegalArgumentException("min > max");
		}
		return new XY(clamp(min, max, xy.x), clamp(min, max, xy.y));
	}
	
	private static int clamp(final int min, final int max, final int val) {
		return Math.max(min, Math.min(max, val));
	}
}
