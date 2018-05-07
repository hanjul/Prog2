package de.hsa.games.fatsquirrel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntityType;

public class CollisionHandler2 implements BiConsumer<Entity, Entity> {

	private final Map<Key, BiConsumer<Entity, Entity>> map = new HashMap<>();
	private final BiConsumer<Entity, Entity> fallthrough;
	
	public CollisionHandler2(final BiConsumer<Entity, Entity> fallthrough) {
		this.fallthrough = fallthrough;
	}
	
	static class Key {
		protected final EntityType c1;
		protected final EntityType c2;

		public Key(final EntityType c1, final EntityType c2) {
			this.c1 = c1;
			this.c2 = c2;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
			result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
			return result;
		}
	}

	static final class SymKey extends Key {

		public SymKey(EntityType c1, EntityType c2) {
			super(c1, c2);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			return c1 == other.c1 && c2 == other.c2;
		}
	}

	static final class AsymKey extends Key {

		public AsymKey(EntityType c1, EntityType c2) {
			super(c1, c2);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			return (c1 == other.c1 && c2 == other.c2) || (c1 == other.c2 && c2 == other.c1);
		}
	}

	public static class Acceptor {
		private final CollisionHandler2 handler;
		private final Key key;
		
		public Acceptor(final CollisionHandler2 handler, final Key key) {
			this.handler = handler;
			this.key = key;
		}
		
		public CollisionHandler2 then(final BiConsumer<Entity, Entity> consumer) {
			handler.map.put(key, consumer);
			return handler;
		}
	}
	
	public  Acceptor sym(final EntityType c1, final EntityType c2) {
		return new Acceptor(this, new SymKey(c1, c2));
	}
	
	public  Acceptor asym(final EntityType c1, final EntityType c2) {
		return new Acceptor(this, new AsymKey(c1, c2));
	}
	
	@Override
	public void accept(Entity t, Entity u) {
		final BiConsumer<Entity, Entity> consumer = map.get(new Key(t.getType(), u.getType()));
		if (consumer == null) {
			fallthrough.accept(t, u);
		} else {
			consumer.accept(t, u);
		}
	}
	
	public static void main(String[] args) {
		BiConsumer<Entity, Entity> fallthrough = (t, u) -> t.setLocation(null);
		CollisionHandler2 defaultOne = new CollisionHandler2(fallthrough).sym(EntityType.MASTER_SQUIRREL, EntityType.WALL).then((player, wall) -> {});

	}
}
