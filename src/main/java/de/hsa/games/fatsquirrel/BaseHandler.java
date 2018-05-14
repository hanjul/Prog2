package de.hsa.games.fatsquirrel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.Character;

public class BaseHandler<B extends Character> implements BiConsumer<B, Entity> {

	private final BiConsumer<B, Entity> defaultConsumer;
	private final Map<Class<?>, BiConsumer<Character, Entity>> map = new HashMap<>();
	
	public BaseHandler(final BiConsumer<B, Entity> defaultConsumer) {
		this.defaultConsumer = defaultConsumer;
	}
	
	public static class Acceptor<B extends Character, E extends Entity> {
		private final BaseHandler handler;
		private final Class<E> key;
		
		public Acceptor(final BaseHandler handler, final Class<E> key) {
			this.handler = handler;
			this.key = key;
		}
		
		@SuppressWarnings("unchecked")
		public <B extends Character, E2 extends Entity> BaseHandler thenCapture(final BiConsumer<B, E2> consumer) {
			handler.map.put(key, (BiConsumer<Character, Entity>) consumer);
			return handler;
		}
		
		@SuppressWarnings("unchecked")
		public BaseHandler then(final BiConsumer<B, ? extends E> consumer) {
			handler.map.put(key, (BiConsumer<Character, Entity>) consumer);
			return handler;
		}
	}

	@Override
	public void accept(Character t, Entity u) {
		final BiConsumer<Character, Entity> consumer = map.get(t.getClass());
		if (consumer == null) {
			for (final Map.Entry<Class<?>, BiConsumer<Character, Entity>> entry : map.entrySet()) {
				if (entry.getKey().isInstance(u)) {
					entry.getValue().accept(t, u);
					return;
				}
			}
//			defaultConsumer.accept(t, u);
		} else {
			consumer.accept(t, u);
		}
	}
	
	public <B1 extends Character, A extends Entity> Acceptor<B, A> on(final Class<A> c) {
		return new Acceptor<>(this, c);
	}
}
