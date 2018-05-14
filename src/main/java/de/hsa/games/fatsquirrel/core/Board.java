package de.hsa.games.fatsquirrel.core;

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
	
	@Override
	public String toString() {
		return flatten().toString();
	}
}
