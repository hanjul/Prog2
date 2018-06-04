package de.hsa.games.fatsquirrel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.SpawnException;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.core.XYSupport;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;

public class TestMiniSquirrel {

	@Test
	public void testStun() {
		final MiniSquirrel mini = new MiniSquirrel(100, XY.ZERO_ZERO);
		final EntityContext context = Mockito.mock(EntityContext.class);
		mini.stun();
		for (int i = 0; i < 4; i++) {
			mini.nextStep(context);
		}
		for (final XY xy : XYSupport.DIRECTIONS) {
			Mockito.verify(context, Mockito.never()).tryMove(mini, xy);
		}
	}
	
	@Test
	public void testHealth() {
		final MiniSquirrel mini = new MiniSquirrel(100, XY.ZERO_ZERO);
		final EntityContext context = Mockito.mock(EntityContext.class);
		for (int i = 0; i < 4; i++) {
			mini.nextStep(context);
		}
		assertEquals(96, mini.getEnergy());
	}
	
	@Test
	public void testSpawnException() {
		assertThrows(SpawnException.class, () -> new MiniSquirrel(0, XY.ZERO_ZERO));
	}
}
