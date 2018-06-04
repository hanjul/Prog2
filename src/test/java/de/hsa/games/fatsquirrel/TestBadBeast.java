package de.hsa.games.fatsquirrel;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.BadBeast;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;

public class TestBadBeast {

	@Test
	public void testAttackMove() {
		final BadBeast beast = new BadBeast(XY.ZERO_ZERO);
		final MiniSquirrel victim = new MiniSquirrel(100, new XY(2, 0));
		final EntityContext context = Mockito.mock(EntityContext.class);
		
		Mockito.when(context.nearestPlayerEntity(XY.ZERO_ZERO)).thenReturn(victim);
		beast.nextStep(context);
		Mockito.verify(context, Mockito.times(1)).tryMove(beast, XY.RIGHT);
	}
	
	@Test
	public void testMaxDistance() {
		final BadBeast beast = new BadBeast(XY.ZERO_ZERO);
		final MiniSquirrel victim = new MiniSquirrel(100, new XY(100, 0));
		final EntityContext context = Mockito.mock(EntityContext.class);
		
		Mockito.when(context.nearestPlayerEntity(XY.ZERO_ZERO)).thenReturn(victim);
		beast.nextStep(context);
		Mockito.verify(context, Mockito.never());
	}
	
	@Test
	public void testMaxSteps() {
		final BadBeast beast = new BadBeast(XY.ZERO_ZERO);
		final MiniSquirrel victim = new MiniSquirrel(100, XY.RIGHT.times(3));
		final EntityContext context = Mockito.mock(EntityContext.class);
		
		Mockito.when(context.nearestPlayerEntity(XY.ZERO_ZERO)).thenReturn(victim);
		for (int i = 0; i < 8; i++) {
			beast.nextStep(context);
		}
		Mockito.verify(context, Mockito.times(2)).tryMove(beast, XY.RIGHT);
	}
}
