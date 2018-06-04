package de.hsa.games.fatsquirrel;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.HandOperatedMasterSquirrel;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;

public class MasterSquirrelTest {

	@Test
	public void testIsOwn() {
		final MasterSquirrel master = new HandOperatedMasterSquirrel(XY.ZERO_ZERO);
		final MiniSquirrel mini = master.createChild(XY.DOWN, 100);
		assertTrue(master.isChild(mini));
	}
}
