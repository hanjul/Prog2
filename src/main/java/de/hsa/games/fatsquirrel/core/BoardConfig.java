package de.hsa.games.fatsquirrel.core;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.util.Assert;

public final class BoardConfig {

	private final Set<String> botNames;
	private final Map<EntityType, Integer> entityAmounts;
	private final XY size;
	private final int steps;
	
	@SuppressWarnings("unused")
	private BoardConfig() {
		botNames = null;
		entityAmounts = null;
		size = null;
		steps = 0;
	}
	
	private BoardConfig(final Builder builder) {
		this(builder.botNames, builder.entityAmounts, builder.size, builder.steps);
	}
	
	public BoardConfig(final Set<String> botNames, final Map<EntityType, Integer> entityAmounts, final XY size, final int steps) {
		for (final Integer amount : entityAmounts.values()) {
			if (amount < 0) {
				throw new IllegalArgumentException("value is < 0");
			}
		}
		this.entityAmounts = Collections.unmodifiableMap(new EnumMap<>(entityAmounts));
		this.size = Objects.requireNonNull(size);
		this.botNames = Collections.unmodifiableSet(botNames);
		this.steps = steps;
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
	
	public Set<String> getBotNames() {
		return botNames;
	}
	
	public int getSteps() {
		return steps;
	}
	
	public static final class Builder {
		private Map<EntityType, Integer> entityAmounts = new EnumMap<>(EntityType.class);
		private XY size;
		private Set<String> botNames = new HashSet<>();
		private int steps;
		
		public Builder putEntityAmount(final EntityType type, final int amount) {
			entityAmounts.put(type, amount);
			return this;
		}
		
		public Builder entityAmounts(final Map<EntityType, Integer> entityAmounts) {
			this.entityAmounts = Assert.notNull(entityAmounts, "entityAmounts must not be null");
			for (final Integer amount : entityAmounts.values()) {
				Assert.isTrue(amount >= 0, "value is < 0");
			}
			return this;
		}
		
		public Builder putBotName(final String name) {
			botNames.add(name);
			return this;
		}
		
		public Builder botNames(final Set<String> botNames) {
			this.botNames = Assert.notNull(botNames, "botNames must not be null");
			for (final String s : botNames) {
				Assert.notNull(s, "botNames must not contain null");
			}
			return this;
		}
		
		public Builder size(final XY size) {
			this.size = Objects.requireNonNull(size);
			return this;
		}
		
		public Builder steps(final int steps) {
			this.steps = steps;
			return this;
		}
		
		public BoardConfig build() {
			return new BoardConfig(this);
		}
	}
}
