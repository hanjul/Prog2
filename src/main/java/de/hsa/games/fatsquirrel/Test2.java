package de.hsa.games.fatsquirrel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.HandOperatedMasterSquirrel;
import de.hsa.games.fatsquirrel.entity.Wall;

public class Test2<E1, E2> implements BiConsumer<E1 , E2> {

	private final Map<Entry<?, ?>, BiConsumer<E1, E2>> map = new HashMap<>();
	
	public static final class Entry<A, B> {
		private final Class<A> c1;
		private final Class<B> c2;
		
		public Entry(final Class<A> c1, final Class<B> c2) {
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

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry<?, ?> other = (Entry<?, ?>) obj;
			if (c1 == null) {
				if (other.c1 != null)
					return false;
			} else if (!c1.equals(other.c1))
				return false;
			if (c2 == null) {
				if (other.c2 != null)
					return false;
			} else if (!c2.equals(other.c2))
				return false;
			return true;
		}
	}
	
	public <A, B> Acceptor<E1, E2, A, B> on(Class<A> c1, Class<B> c2) {
		return new Acceptor<>(this, new Entry<>(c1, c2));
	}
	
	static class Acceptor<E1, E2, E3, E4> {
		@SuppressWarnings("rawtypes")
		private final Test2 t;
		private final Entry<E3, E4> e;
		
		Acceptor(Test2<E1, E2> t, Entry<E3, E4> e) {
			this.t = t;
			this.e = e;
		}
		
		@SuppressWarnings("unchecked")
		public Test2<E1, E2> then(BiConsumer<E3, E4> b) {
			t.map.put(e, b);
			return t;
		}
	}

	@Override
	public void accept(E1 t, E2 u) {
		map.get(new Entry<>(t.getClass(), u.getClass())).accept(t, u);
	}
	
	public static void main(String[] args) {
		BiConsumer<Entity, Entity> fall = new Test2<Entity, Entity>().on(HandOperatedMasterSquirrel.class, Wall.class).then((master, wall) -> {});
		fall.accept(null, null);
	}
}