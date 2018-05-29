package de.hsa.games.fatsquirrel.core;

import java.util.ArrayList;
import java.util.List;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntitySet;
import de.hsa.games.fatsquirrel.util.Assert;

public final class Board {

	private final BoardConfig config;
	private final EntitySet entities;

	public Board(final BoardConfig config, final EntitySet entities) {
		this.config = Assert.notNull(config, "config must not be null");
		this.entities = Assert.notNull(entities, "entities must not be null");
	}
	
	public BoardConfig getConfig() {
		return config;
	}
	
	public EntitySet getEntities() {
		return entities;
	}
	
	public FlattenedBoard flatten() {
		return new FlattenedBoard(this);
	}
	
	public <T> List<T> getAll(final Class<T> c) {
		final List<T> result = new ArrayList<>();
		for (final Entity e : entities) {
			if (e.getClass() == c) {
				result.add(cast(e));
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T cast(final Object o) {
		return (T) o;
	}
	
	@Override
	public String toString() {
		return flatten().toString();
	}

	public void put(MasterSquirrelBot masterSquirrelBot) {
		entities.add(masterSquirrelBot);
	}
}
