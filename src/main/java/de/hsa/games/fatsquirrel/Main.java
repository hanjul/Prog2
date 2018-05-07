package de.hsa.games.fatsquirrel;

import java.util.EnumMap;
import java.util.Map;

import de.hsa.games.fatsquirrel.console.ConsoleGame;
import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.BoardConfig;
import de.hsa.games.fatsquirrel.core.BoardCreator;
import de.hsa.games.fatsquirrel.core.XY;
import de.hsa.games.fatsquirrel.entity.EntityType;

public class Main {

	public static void main(String[] args) {
		Map<EntityType, Integer> m = new EnumMap<>(EntityType.class);
		m.put(EntityType.WALL, 2);
		m.put(EntityType.GOOD_BEAST, 1);
		m.put(EntityType.BAD_BEAST, 2);
		m.put(EntityType.GOOD_PLANT, 2);
		m.put(EntityType.BAD_PLANT, 2);
		m.put(EntityType.MASTER_SQUIRREL, 1);
		BoardConfig c = new BoardConfig(m, new XY(10, 8), 5);
		Board b = new BoardCreator(0, c).generateBoard();
		State s = new State(b);
		new ConsoleGame(s).run();
	}
}
