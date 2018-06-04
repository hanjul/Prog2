package de.hsa.games.fatsquirrel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.Test;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.BoardConfig;
import de.hsa.games.fatsquirrel.core.FlattenedBoard;
import de.hsa.games.fatsquirrel.core.SpawnException;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.BadPlant;
import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntitySet;
import de.hsa.games.fatsquirrel.entity.GoodBeast;
import de.hsa.games.fatsquirrel.entity.GoodPlant;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;
import de.hsa.games.fatsquirrel.entity.Wall;

public class TestFlattenedBoard {
	
	@Test
	public void testStun() {
		final FlattenedBoard flat = createBoard(new Wall(XY.ZERO_ZERO));
		
		final MiniSquirrel mini = new MiniSquirrel(100, XY.RIGHT);
		flat.tryMove(mini, XY.LEFT);
		assertTrue(!mini.canMove());
	}
	
	@Test
	public void testGoodPlant() {
		final FlattenedBoard flat = createBoard(new GoodPlant(XY.ZERO_ZERO));
		
		final MiniSquirrel mini = new MiniSquirrel(100, XY.RIGHT);
		flat.spawnMiniSquirrel(mini);
		
		flat.tryMove(mini, XY.LEFT);
		assertEquals(200, mini.getEnergy());
		System.out.println(flat.getEntityType(0, 0));
		assertEquals(XY.ZERO_ZERO, mini.getLocation());
	}
	
	@Test
	public void testBadPlant() {
		final FlattenedBoard flat = createBoard(new BadPlant(XY.ZERO_ZERO));
		
		final MiniSquirrel mini = new MiniSquirrel(200, XY.RIGHT);
		flat.spawnMiniSquirrel(mini);
		
		flat.tryMove(mini, XY.LEFT);
		assertEquals(100, mini.getEnergy());
		assertEquals(XY.ZERO_ZERO, mini.getLocation());
	}
	
	@Test
	public void testGoodBeast() {
		final FlattenedBoard flat = createBoard(new GoodBeast(XY.ZERO_ZERO));
		
		final MiniSquirrel mini = new MiniSquirrel(100, XY.RIGHT);
		flat.spawnMiniSquirrel(mini);
		
		flat.tryMove(mini, XY.LEFT);
		assertEquals(300, mini.getEnergy());
		assertEquals(XY.ZERO_ZERO, mini.getLocation());
	}
	
	@Test
	public void testSpawnException() {
		final FlattenedBoard flat = createBoard(new Wall(XY.ZERO_ZERO));
		final MiniSquirrel mini = new MiniSquirrel(100, XY.ZERO_ZERO);
		
		assertThrows(SpawnException.class, () -> flat.spawnMiniSquirrel(mini));
	}
	
	private static FlattenedBoard createBoard(final Entity... entities) {
		final EntitySet set = new EntitySet();
		for (final Entity e : entities) {
			set.add(e);
		}
		final BoardConfig config = new BoardConfig.Builder().size(new XY(2,2)).build();
		final Board board = new Board(config, set, generateRandom());
		final FlattenedBoard flat = new FlattenedBoard(board);
		return flat;
	}
	
	private static Random generateRandom() {
		return new Random(0);
	}
}
