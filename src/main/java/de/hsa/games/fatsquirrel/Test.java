package de.hsa.games.fatsquirrel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.entity.HandOperatedMasterSquirrel;
import de.hsa.games.fatsquirrel.entity.Wall;

public class Test {

	private static final Map<Entry2<?, ?>, BiConsumer<? extends Entity, ? extends Entity>> functions = new HashMap<>();

	public static final class Entry2<E1 extends Entity, E2 extends Entity> {
		
		private final Class<E1> c1;
		private final Class<E2> c2;
		
		public Entry2(final Class<E1> c1, final Class<E2> c2) {
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
			Entry2<?, ?> other = (Entry2<?, ?>) obj;
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
	
	public static final class Entry {
		
		private final EntityType left;
		private final EntityType right;
		
		public Entry(final EntityType left, final EntityType right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((left == null) ? 0 : left.hashCode());
			result = prime * result + ((right == null) ? 0 : right.hashCode());
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
			Entry other = (Entry) obj;
			if (left != other.left)
				return false;
			if (right != other.right)
				return false;
			return true;
		}
	}
	
	public static void main(String[] args) {
		functions.put(new Entry2<>(HandOperatedMasterSquirrel.class, Wall.class), (e1, e2) -> {});
	}
}
