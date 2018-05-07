package de.hsa.games.fatsquirrel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.Character;
import de.hsa.games.fatsquirrel.entity.PlayerEntity;
import de.hsa.games.fatsquirrel.entity.Wall;

public class CollisionHandler implements BiConsumer<Character, Entity> {

	private final Map<Key<?, ?>, BiConsumer<Character, Entity>> map = new HashMap<>();
	private final BiConsumer<Character, Entity> fallthrough;
	
	public CollisionHandler(final BiConsumer<Character, Entity> fallthrough) {
		this.fallthrough = fallthrough;
	}
	
	static class Key<A, B> {
		protected final Class<A> c1;
		protected final Class<B> c2;

		public Key(final Class<A> c1, final Class<B> c2) {
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
		
		public boolean poly(Character t, Entity u) {
			return false;
		}
	}

	static final class SymKey<A, B> extends Key<A, B> {

		public SymKey(Class<A> c1, Class<B> c2) {
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
			Key<?, ?> other = (Key<?, ?>) obj;
			return c1 == other.c1 && c2 == other.c2;
		}
		
		@Override
		public boolean poly(Character t, Entity u) {
			return c1.isInstance(t) && c2.isInstance(u);
		}
	}

	static final class AsymKey<A, B> extends Key<A, B> {

		public AsymKey(Class<A> c1, Class<B> c2) {
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
			Key<?, ?> other = (Key<?, ?>) obj;
			return (c1 == other.c1 && c2 == other.c2) || (c1 == other.c2 && c2 == other.c1);
		}
		
		@Override
		public boolean poly(Character t, Entity u) {
			return (c1.isInstance(t) && c2.isInstance(u)) || (c2.isInstance(t) && c1.isInstance(u));
		}
	}

	public static class Acceptor<E1 extends Entity, E2 extends Entity> {
		private final CollisionHandler handler;
		private final Key<E1, E2> key;
		
		public Acceptor(final CollisionHandler handler, final Key<E1, E2> key) {
			this.handler = handler;
			this.key = key;
		}
		
		@SuppressWarnings("unchecked")
		public <E3 extends Character, E4 extends Entity> CollisionHandler thenCapture(final BiConsumer<E3, E4> consumer) {
			handler.map.put(key, (BiConsumer<Character, Entity>) consumer);
			return handler;
		}
		
		@SuppressWarnings("unchecked")
		public CollisionHandler then(final BiConsumer<? extends E1, ? extends E2> consumer) {
			handler.map.put(key, (BiConsumer<Character, Entity>) consumer);
			return handler;
		}
	}
	
	public <A extends Character, B extends Entity> Acceptor<A, B> sym(final Class<A> c1, final Class<B> c2) {
		return new Acceptor<>(this, new SymKey<>(c1, c2));
	}
	
	public <A extends Character, B extends Entity> Acceptor<A, B> asym(final Class<A> c1, final Class<B> c2) {
		return new Acceptor<>(this, new AsymKey<>(c1, c2));
	}
	
	@Override
	public void accept(Character t, Entity u) {
		final BiConsumer<Character, Entity> consumer = map.get(new Key<>(t.getClass(), u.getClass()));
		if (consumer == null) {
			fallthrough.accept(t, u);
		} else {
			for (final Map.Entry<Key<?, ?>, BiConsumer<Character, Entity>> entry : map.entrySet()) {
				if (entry.getKey().poly(t, u)) {
					entry.getValue().accept(t, u);
					return;
				}
			}
			consumer.accept(t, u);
		}
	}
	
	public static void main(String[] args) {
		BiConsumer<Character, Entity> fallthrough = (t, u) -> t.setLocation(null);
		@SuppressWarnings("unused")
		CollisionHandler defaultOne = new CollisionHandler(fallthrough).sym(PlayerEntity.class, Wall.class).then((player, wall) -> {});
	}
}
