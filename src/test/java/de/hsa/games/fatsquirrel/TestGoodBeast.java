package de.hsa.games.fatsquirrel;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.GoodBeast;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;

public class TestGoodBeast {

	@Test
	public void testRunAway() {
		final GoodBeast beast = new GoodBeast(XY.ZERO_ZERO);
		final MiniSquirrel attacker = new MiniSquirrel(100, XY.RIGHT);
		final EntityContext context = Mockito.mock(EntityContext.class);
		
		Mockito.when(context.nearestPlayerEntity(XY.ZERO_ZERO)).thenReturn(attacker);
		beast.nextStep(context);
		Mockito.verify(context, Mockito.times(1)).tryMove(beast, XY.LEFT);
	}
	
	@Test
	public void testMaxDistance() {
		final GoodBeast beast = new GoodBeast(XY.ZERO_ZERO);
		final MiniSquirrel attacker = new MiniSquirrel(100, new XY(100, 0));
		final EntityContext context = Mockito.mock(EntityContext.class);
		
		Mockito.when(context.nearestPlayerEntity(XY.ZERO_ZERO)).thenReturn(attacker);
		beast.nextStep(context);
		Mockito.verify(context, Mockito.never()).tryMove(beast, XY.LEFT);;
	}
	
	@Test
	public void testMaxSteps() {
		final GoodBeast beast = new GoodBeast(XY.ZERO_ZERO);
		final MiniSquirrel attacker = new MiniSquirrel(100, XY.RIGHT);
		final EntityContext context = Mockito.mock(EntityContext.class);
		
		Mockito.when(context.nearestPlayerEntity(XY.ZERO_ZERO)).thenReturn(attacker);
		for (int i = 0; i < 8; i++) {
			beast.nextStep(context);
		}
		Mockito.verify(context, Mockito.times(2)).tryMove(beast, XY.LEFT);
	}
}
