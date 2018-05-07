package de.hsa.games.fatsquirrel.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hsa.games.fatsquirrel.entity.BadBeast;
import de.hsa.games.fatsquirrel.entity.BadPlant;
import de.hsa.games.fatsquirrel.entity.Entity;
import de.hsa.games.fatsquirrel.entity.EntitySet;
import de.hsa.games.fatsquirrel.entity.EntityType;
import de.hsa.games.fatsquirrel.entity.GoodBeast;
import de.hsa.games.fatsquirrel.entity.GoodPlant;
import de.hsa.games.fatsquirrel.entity.MasterSquirrel;
import de.hsa.games.fatsquirrel.entity.MiniSquirrel;
import de.hsa.games.fatsquirrel.entity.PlayerEntity;
import de.hsa.games.fatsquirrel.util.Assert;

public class FlattenedBoard implements BoardView, EntityContext {

	private final Board board;
	private final Entity[][] cells;

	public FlattenedBoard(final Board board) {
		this.board = Assert.notNull(board, "board must not be null");
		final XY size = board.getConfig().getSize();
		cells = new Entity[size.getX()][size.getY()];
		for (final Entity e : board.getEntities()) {
			final XY location = e.getLocation();
			cells[location.getX()][location.getY()] = e;
		}
	}

	private static void rangeCheck(final int index, final int max) {
		if (index < 0 || index >= max) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Max: " + max);
		}
	}

	@Override
	public EntityType getEntityType(int x, int y) {
		final XY size = getSize();
		rangeCheck(x, size.getX());
		rangeCheck(y, size.getY());
		return cells[x][y] == null ? null : cells[x][y].getType();
	}

	@Override
	public XY getSize() {
		return board.getConfig().getSize();
	}

	private void eat(final PlayerEntity player, final Entity entity) {
		player.updateEnergy(entity.getEnergy());
		killAndReplace(entity);
	}

	private void bitten(final PlayerEntity player, final BadBeast beast) {
		player.updateEnergy(beast.getEnergy());
		beast.removeLive();
		if (beast.getLivesLeft() <= 0) {
			killAndReplace(beast);
		}
	}

	@Override
	public void tryMove(MiniSquirrel squirrel, XY moveDirection) {
		if (squirrel.getEnergy() < 0) {
			kill(squirrel);
		}
		final Entity collide = tryCollide(squirrel, moveDirection);
		if (collide != null) {
			switch (collide.getType()) {
			case WALL:
				squirrel.stun();
				break;
			case BAD_BEAST:
				bitten(squirrel, (BadBeast) collide);
				break;
			case BAD_PLANT:
				eat(squirrel, collide);
				break;
			case GOOD_BEAST:
				eat(squirrel, collide);
				break;
			case GOOD_PLANT:
				eat(squirrel, collide);
				break;
			case MASTER_SQUIRREL:
				final MasterSquirrel master = (MasterSquirrel) collide;
				if (master.isChild(squirrel)) {
					master.updateEnergy(squirrel.getEnergy());
					kill(squirrel);
				} else {
					kill(squirrel);
				}
				break;
			case MINI_SQUIRREL:
				final MiniSquirrel mini = (MiniSquirrel) collide;
				if (!squirrel.isSibling(mini)) {
					kill(mini);
					kill(squirrel);
				}
				break;
			default:
				break;
			}
		}
		finishMove(squirrel, moveDirection);
	}

	@Override
	public void tryMove(GoodBeast beast, XY moveDirection) {
		final Entity collide = tryCollide(beast, moveDirection);
		if (collide != null) {
			switch (collide.getType()) {
			case WALL:
				break;
			case BAD_BEAST:
				break;
			case BAD_PLANT:
				break;
			case GOOD_BEAST:
				break;
			case GOOD_PLANT:
				break;
			case MASTER_SQUIRREL:
				eat((PlayerEntity) collide, beast);
				break;
			case MINI_SQUIRREL:
				eat((PlayerEntity) collide, beast);
				break;
			default:
				break;
			}
		}
		finishMove(beast, moveDirection);
	}

	@Override
	public void tryMove(BadBeast beast, XY moveDirection) {
		final Entity collide = tryCollide(beast, moveDirection);
		if (collide != null) {
			switch (collide.getType()) {
			case WALL:
				break;
			case BAD_BEAST:
				break;
			case BAD_PLANT:
				break;
			case GOOD_BEAST:
				break;
			case GOOD_PLANT:
				break;
			case MASTER_SQUIRREL:
				bitten((PlayerEntity) collide, beast);
				break;
			case MINI_SQUIRREL:
				bitten((PlayerEntity) collide, beast);
				break;
			default:
				break;
			}
		}
		finishMove(beast, moveDirection);
	}

	@SuppressWarnings("unused")
	private void move(final Entity e, final XY direction) {
		XY loc = e.getLocation();
		e.setLocation(loc.add(direction));
		cells[loc.getX()][loc.getY()] = null;
		loc = loc.add(direction);
		cells[loc.getX()][loc.getY()] = e;
	}

	@Override
	public void tryMove(MasterSquirrel master, XY moveDirection) {
		final Entity collide = tryCollide(master, moveDirection);
		if (collide != null) {
			switch (collide.getType()) {
			case WALL:
				master.stun();
				break;
			case BAD_PLANT:
				master.updateEnergy(collide.getEnergy());
				killAndReplace(collide);
				break;
			case GOOD_PLANT:
				master.updateEnergy(collide.getEnergy());
				killAndReplace(collide);
				break;
			case MINI_SQUIRREL: {
				final MiniSquirrel mini = (MiniSquirrel) collide;
				if (master.isChild(mini)) {
					master.updateEnergy(mini.getEnergy());
					kill(mini);
				} else {
					master.updateEnergy(150);
					kill(mini);
				}
			}
			case BAD_BEAST:
				bitten(master, (BadBeast) collide);
				break;
			case GOOD_BEAST:
				eat(master, collide);
				break;
			case MASTER_SQUIRREL:
				break;
			default:
				break;
			}
		}
		finishMove(master, moveDirection);
	}

	/**
	 * Attempts to move the {@code Entity} in the specified direction. If the
	 * desired location is already occupied, the method will return the
	 * {@code Entity} at that location, otherwise {@code null}.
	 * 
	 * @param e
	 *            the Entity to move
	 * @param direction
	 *            the direction in which the Entity is to be moved
	 * @return the Entity at the desired location or null
	 */
	private Entity tryCollide(final Entity e, final XY direction) {
		final XY resultLocation = e.getLocation().add(direction);
		return cells[resultLocation.getX()][resultLocation.getY()];
	}

	private void finishMove(final Entity e, final XY direction) {
		final XY location = e.getLocation();
		if (cells[location.getX()][location.getY()] != null) {
			if (tryCollide(e, direction) == null) {
				e.setLocation(e.getLocation().add(direction));
				cells[location.getX()][location.getY()] = null;
				cells[e.getLocation().getX()][e.getLocation().getY()] = e;
			}
		}
	}

	@Override
	public PlayerEntity nearestPlayerEntity(XY pos) {
		PlayerEntity result = null;
		double distance = Double.MAX_VALUE;
		for (final Entity e : board.getEntities()) {
			if (e instanceof PlayerEntity) {
				final XY location = e.getLocation();
				final double dist = pos.minus(location).length();
				if (dist < distance) {
					distance = dist;
					result = (PlayerEntity) e;
				}
			}
		}
		return result;
	}

	@Override
	public void kill(Entity entity) {
		Assert.notNull(entity, "entity must not be null");
		final EntitySet set = board.getEntities();
		set.remove(entity);
		final XY location = entity.getLocation();
		cells[location.getX()][location.getY()] = null;
	}

	@Override
	public void killAndReplace(Entity entity) {
		kill(entity);
		final EntityType type = entity.getType();
		final XY rand = randomPosition();
		Entity e = null;
		if (type == EntityType.GOOD_PLANT) {
			e = new GoodPlant(-1, rand);
		}
		if (type == EntityType.BAD_PLANT) {
			e = new BadPlant(-1, rand);
		}
		if (type == EntityType.GOOD_BEAST) {
			e = new GoodBeast(-1, rand);
		}
		if (type == EntityType.BAD_BEAST) {
			e = new BadBeast(-1, rand);
		}
		if (e != null) {
			board.getEntities().add(e);
			cells[rand.getX()][rand.getY()] = e;
		}
	}

	private XY randomPosition() {
		final List<XY> locations = getRemainingLocations();
		if (locations.isEmpty()) {
			throw new IllegalStateException("the board is full");
		}
		Collections.shuffle(locations);
		return locations.get(0);
	}

	private List<XY> getRemainingLocations() {
		final XY size = board.getConfig().getSize();
		final List<XY> result = new ArrayList<>();
		for (int x = 0; x < size.getX(); x++) {
			for (int y = 0; y < size.getY(); y++) {
				if (cells[x][y] == null) {
					result.add(new XY(x, y));
				}
			}
		}
		return result;
	}

	public Board getBoard() {
		return board;
	}

	@Override
	public EntityType getEntityType(XY pos) {
		Assert.notNull(pos, "pos must not be null");
		return getEntityType(pos.getX(), pos.getY());
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < cells.length; i++) {
			builder.append('[');
			for (int j = 0; j < cells[i].length; j++) {
				final Entity e = cells[i][j];
				if (e == null) {
					builder.append(' ');
				} else {
					builder.append(e.getType().ordinal());
				}
			}
			builder.append(']').append('\n');
		}
		return super.toString();
	}
}
