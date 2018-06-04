package de.hsa.games.fatsquirrel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import de.hsa.games.fatsquirrel.core.XY;

public class XYTest {

	@Test
	public void testLength() {
		assertEquals(XY.UP.length(), 1);
		assertEquals(XY.RIGHT.length(), 1);
	}
	
	@Test
	public void testPlus() {
		assertEquals(XY.UP.plus(XY.RIGHT), XY.RIGHT_UP);
	}
	
	@Test
	public void testMinus() {
		assertEquals(XY.UP.minus(XY.UP), XY.ZERO_ZERO);
	}
	
	@Test
	public void testTimes() {
		assertEquals(XY.UP.times(2), new XY(0, -2));
	}
	
	@Test
	public void testDistanceFrom() {
		assertEquals(XY.UP.distanceFrom(XY.UP), 0);
		assertEquals(XY.ZERO_ZERO.distanceFrom(XY.UP), 1);
	}
}
