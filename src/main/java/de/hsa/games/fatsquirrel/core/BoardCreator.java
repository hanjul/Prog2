package de.hsa.games.fatsquirrel.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hsa.games.fatsquirrel.entity.BadBeast;
import de.hsa.games.fatsquirrel.entity.BadPlant;
import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntitySet;
import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.entity.GoodBeast;
import de.hsa.games.fatsquirrel.entity.HandOperatedMasterSquirrel;
import de.hsa.games.fatsquirrel.entity.Wall;
import de.hsa.games.fatsquirrel.util.Assert;

public final class BoardCreator {

	private final Random random;
	private final long seed;
	private final BoardConfig config;

	public BoardCreator(final long seed, final BoardConfig config) {
		this.seed = seed;
		this.config = Assert.notNull(config, "config must not be null");
		random = new Random(seed);
	}

	public long getSeed() {
		return seed;
	}

	public BoardConfig getConfig() {
		return config;
	}

	private EntitySet generateWalls() {
		final EntitySet set = new EntitySet();
		final XY size = config.getSize();
		for (int x = 0; x < size.x; x++) {
			for (int y = 0; y < size.y; y++) {
				if ((x == 0 || y == 0) || (x == size.x - 1 || y == size.y - 1)) {
					set.add(new Wall(new XY(x, y)));
				}
			}
		}
		return set;
	}

	private List<XY> generateLocations() {
		final XY size = config.getSize();
		// final int innerSize = (size.x - 2) * (size.y - 2);
		final List<XY> locations = new ArrayList<>();
		for (int x = 1; x < size.x - 1; x++) {
			for (int y = 1; y < size.y - 1; y++) {
				locations.add(new XY(x, y));
			}
		}
		return locations;
	}

	private static Entity generateEntity(final EntityType type, final XY location) {
		switch (type) {
		case BAD_BEAST:
			return new BadBeast(location);
		case BAD_PLANT:
			return new BadPlant(location);
		case GOOD_BEAST:
			return new GoodBeast(location);
		case GOOD_PLANT:
			return new BadPlant(location);
		case MASTER_SQUIRREL:
			return new HandOperatedMasterSquirrel(location);
		case MINI_SQUIRREL:
			return null;
		case WALL:
			return new Wall(location);
		default:
			return null;
		}
	}

	public Board generateBoard() {
		final EntitySet set = generateWalls();
		final List<XY> locations = generateLocations();
		Collections.shuffle(locations, random);
		int index = 0;
		
		for (final Map.Entry<EntityType, Integer> e : config.getEntityAmounts().entrySet()) {
			for (int i = 0; i < e.getValue(); i++) {
				final Entity generated = generateEntity(e.getKey(), locations.get(index++));
				if (generated != null) {
					set.add(generated);
				}
			}
		}
		return new Board(config, set, random);
	}
}
