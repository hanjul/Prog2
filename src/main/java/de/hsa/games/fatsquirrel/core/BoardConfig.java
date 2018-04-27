package de.hsa.games.fatsquirrel.core;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.util.Assert;

public final class BoardConfig {

	private final Map<EntityType, Integer> entityAmounts;
	private final XY size;
	private final int wallCount;
	
	private BoardConfig(final Builder builder) {
		this(builder.entityAmounts, builder.size, builder.wallCount);
	}
	
	public BoardConfig(final Map<EntityType, Integer> entityAmounts, final XY size, final int wallCount) {
		for (final Integer amount : entityAmounts.values()) {
			if (amount < 0) {
				throw new IllegalArgumentException("value is < 0");
			}
		}
		this.entityAmounts = Collections.synchronizedMap(new EnumMap<>(entityAmounts));
		this.size = Objects.requireNonNull(size);
		Assert.isTrue(wallCount >= 0, "wallCount must be >= 0");
		this.wallCount = wallCount;
	}
	
	/**
	 * Returns an unmodifiable {@link Map} which maps 
	 * 
	 * @return
	 */
	public Map<EntityType, Integer> getEntityAmounts() {
		return entityAmounts;
	}
	
	public XY getSize() {
		return size;
	}
	
	public int getWallCount() {
		return wallCount;
	}
	
	public static final class Builder {
		private Map<EntityType, Integer> entityAmounts = new EnumMap<>(EntityType.class);
		private XY size;
		private int wallCount;
		
		public Builder putEntityAmount(final EntityType type, final int amount) {
			return this;
		}
		
		public Builder entityAmounts(final Map<EntityType, Integer> entityAmounts) {
			this.entityAmounts = Assert.notNull(entityAmounts, "entityAmounts must not be null");
			for (final Integer amount : entityAmounts.values()) {
				Assert.isTrue(amount >= 0, "value is < 0");
			}
			return this;
		}
		
		public Builder size(final XY size) {
			this.size = Objects.requireNonNull(size);
			return this;
		}
		
		public Builder wallCount(final int wallCount) {
			Assert.isTrue(wallCount >= 0, "wallCount must be >= 0");
			this.wallCount = wallCount;
			return this;
		}
		
		public BoardConfig build() {
			return new BoardConfig(this);
		}
	}
}
