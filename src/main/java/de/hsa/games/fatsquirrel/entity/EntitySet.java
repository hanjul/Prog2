package de.hsa.games.fatsquirrel.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.hsa.games.fatsquirrel.core.EntityContext;
import de.hsa.games.fatsquirrel.util.Assert;

public class EntitySet implements Set<Entity> {

	private final Set<Entity> inner = new HashSet<>();

	@Override
	public int size() {
		return inner.size();
	}

	@Override
	public boolean isEmpty() {
		return inner.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return inner.contains(o);
	}

	@Override
	public Iterator<Entity> iterator() {
		return inner.iterator();
	}

	@Override
	public Object[] toArray() {
		return inner.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return inner.toArray(a);
	}

	@Override
	public boolean add(Entity e) {
		Assert.notNull(e, "e must not be null");
		return inner.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return inner.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return inner.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Entity> c) {
		for (final Entity e : c) {
			Assert.notNull(e, "c must not contain null elements");
		}
		return inner.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return inner.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return inner.removeAll(c);
	}

	@Override
	public void clear() {
		inner.clear();
	}

	@Override
	public String toString() {
		return inner.toString();
	}

	public void nextStep(final EntityContext context) {
		Assert.notNull(context, "context must not be null");
		stream().filter(e -> e instanceof Character).forEach((e -> ((Character) e).nextStep(context)));
	}
}
